package com.example.bookurbook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookurbook.MailAPISource.JavaMailAPI;
import com.example.bookurbook.models.Admin;
import com.example.bookurbook.models.Chat;
import com.example.bookurbook.models.Message;
import com.example.bookurbook.models.Post;
import com.example.bookurbook.models.PostList;
import com.example.bookurbook.models.RegularUser;
import com.example.bookurbook.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatActivity extends AppCompatActivity implements ReportPostDialogListener
{
    //variables
    private User currentUser;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private CollectionReference msgRef;
    private Chat currentChat;
    private ArrayList<Message> messages;
    private Date date;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    private RecyclerView recyclerView;
    private MessageAdapter messageAdapter;
    private ImageView sendButton;
    private EditText messageBox;
    private Toolbar toolbar;
    private ImageButton homeButton;
    private ImageButton reportButton;
    private ImageButton blockButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        if(getIntent().getSerializableExtra("currentUser") instanceof Admin)
        {
            currentUser = (Admin) getIntent().getSerializableExtra("currentUser");
        }
        else
        {
            currentUser = (RegularUser) getIntent().getSerializableExtra("currentUser");
        }

        currentChat = (Chat) getIntent().getSerializableExtra("clickedChat");
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        timeFormat = new SimpleDateFormat("HH:mm");
        sendButton = findViewById(R.id.send_message_button);
        messageBox = findViewById(R.id.message_box);
        homeButton = findViewById(R.id.homeButton);
        reportButton = findViewById(R.id.reportButton);
        blockButton = findViewById(R.id.blockButton);

        toolbar = findViewById(R.id.toolbar_chat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Chat with " + currentChat.getUser2().getUsername());

        msgRef = db.collection("messages").document(currentChat.getChatID()).collection("messagetree");
        msgRef.addSnapshotListener(new EventListener<QuerySnapshot>()
        {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error)
            {
                if ( error != null)
                {
                    Toast chatError = Toast.makeText(ChatActivity.this,"Something is wrong. Please check your Internet connection.", Toast.LENGTH_LONG);
                    chatError.show();
                    return;
                }
                    messages = new ArrayList<Message>();
                    for (QueryDocumentSnapshot doc : value)
                    {
                        Message msgData = new Message(doc.getString("sendBy"), doc.getString("content"),
                            dateFormat.format(doc.getDate("date")), timeFormat.format(doc.getDate("date")));
                        msgData.setDate(doc.getDate("date"));
                        messages.add(msgData);
                    }
                    //Create gui by using messages
                    Collections.sort(messages);
                    buildRecyclerView();
                    recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
            }
        });

        //Message sending
        sendButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                db.collection("users").whereEqualTo("username", currentChat.getUser2().getUsername()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if( task.isSuccessful())
                        {
                            for( DocumentSnapshot doc :task.getResult() )
                            {
                                List<String> usersBlockedByOther = (List<String>) doc.get("blockedusers");
                                if( usersBlockedByOther != null && usersBlockedByOther.contains(currentUser.getUsername()))
                                {
                                    Toast chatError = Toast.makeText(ChatActivity.this,"You are blocked by this user.", Toast.LENGTH_LONG);
                                    chatError.show();
                                }
                                else
                                {
                                    date = new Date();
                                    Message message = new Message(currentUser.getUsername(), messageBox.getText().toString(), dateFormat.format(date), timeFormat.format(date));
                                    if( !messageBox.getText().toString().equals("") )
                                    {
                                        sendMessageToDatabase(message, date);
                                        messageBox.setText("");
                                    }
                                }
                            }
                        }
                    }
                });
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent startIntent = new Intent(ChatActivity.this, MainMenuActivity.class);
                if ( currentChat.getChatID().indexOf(currentUser.getUsername()) == 0)
                {
                    db.collection("chats").document(currentChat.getChatID()).update("readbyuser1", true);
                }
                else
                {
                    db.collection("chats").document(currentChat.getChatID()).update("readbyuser2", true);
                }
                startIntent.putExtra("currentUser" , currentUser);
                startActivity(startIntent);
            }
        });

        reportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPostReportDialog();
            }
        });

        blockButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                db.collection("users").document(auth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        List<String> blockedUsernames = Collections.emptyList();
                        blockedUsernames = (List<String>) documentSnapshot.get("blockedusers");
                        blockedUsernames.add(currentChat.getUser2().getUsername());
                        ArrayList<String> toBePassed = getIntent().getStringArrayListExtra("blockedUsernames");
                        toBePassed.add(currentChat.getUser2().getUsername());
                        HashMap<String, Object> newData = new HashMap<>();
                        newData.put("blockedusers", blockedUsernames);
                        db.collection("users").document(auth.getCurrentUser().getUid()).set(newData, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ChatActivity.this, currentChat.getUser2().getUsername() + " has been blocked.", Toast.LENGTH_SHORT).show();
                                Intent pass = new Intent(ChatActivity.this, MyChatsActivity.class);
                                pass.putExtra("currentUser", currentUser);
                                pass.putExtra("blockedUsernames", toBePassed);
                                startActivity(pass);
                            }
                        });

                    }
                });
            }
        });
    }

    private void buildRecyclerView()
    {
        recyclerView = findViewById(R.id.my_chats_recycler_id);
        messageAdapter = new MessageAdapter(ChatActivity.this, messages, currentChat);
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
        messageAdapter.notifyDataSetChanged();
    }

    public void sendMessageToDatabase( Message msg, Date msgdate )
    {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> chatData = new HashMap<>();
        data.put("sendBy", msg.getSentBy());
        data.put("content", msg.getContent());
        data.put("date", msgdate);
        chatData.put("lastmessage", msg.getContent());
        chatData.put("lastmessagedate", msgdate);
        if( currentChat.getChatID().indexOf(currentUser.getUsername()) == 0)
        {
            chatData.put("readbyuser1", true);
            chatData.put("readbyuser2", false);
        }
        else
        {
            chatData.put("readbyuser1", false);
            chatData.put("readbyuser2", true);
        }
        msgRef.add(data).addOnCompleteListener(new OnCompleteListener<DocumentReference>()
        {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task)
            {
                db.collection("chats").document(currentChat.getChatID())
                        .update(chatData).addOnSuccessListener(new OnSuccessListener<Void>()
                {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        System.out.println("Database updated.");
                    }
                });
            }
        });
    }

    public void openPostReportDialog()
    {
        ReportDialog dialog = new ReportDialog();
        dialog.show(getSupportFragmentManager(), "");
    }

    @Override
    public void applyTexts(String description, String category) {
        currentChat.getUser2().report(description, category);
        currentChat.getUser2().setReportNum(currentUser.getReportNum()+1);
        String reportDetails = "";
        for(int i = 0; messages.size() > i; i++)
        {
            if(messages.get(i).getSentBy().equals(currentChat.getUser1().getUsername()))
                reportDetails = reportDetails + currentChat.getUser1().getUsername() + ": " + messages.get(i).getContent() + " \uD83D\uDD52 sent in " + messages.get(i).getMessageDate() + "\n\n";
            else
                reportDetails = reportDetails + currentChat.getUser2().getUsername() + ": " + messages.get(i).getContent() + " \uD83D\uDD52 sent in " + messages.get(i).getMessageDate() + "\n\n";
        }
        reportDetails = reportDetails + " This report is categorized as " + category + " and described as " + description + ".";
        JavaMailAPI reportPost = new JavaMailAPI(ChatActivity.this, "vvcbookurbook@gmail.com", currentChat.getUser2().getUsername() + " CHAT REPORT", reportDetails);
        reportPost.execute();
        db.collection("users").whereEqualTo("username", currentChat.getUser2().getUsername()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(DocumentSnapshot doc : task.getResult())
                {
                    String reportedUserID = doc.getId();
                    int currentReportCount = doc.getLong("reports").intValue() + 1;
                    HashMap<String, Object> newData = new HashMap<>();
                    newData.put("reports", currentReportCount);
                    db.collection("users").document(reportedUserID).set(newData, SetOptions.merge());
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent pass;
        if((boolean) getIntent().getExtras().get("fromPostActivity")) {
            pass = new Intent(ChatActivity.this, PostActivity.class);
            pass.putExtra("postlist", (PostList) getIntent().getSerializableExtra("postlist"));
            pass.putExtra("post", (Post) getIntent().getSerializableExtra("post"));
            pass.putExtra("fromPostList", true);
            pass.putExtra("previousActivity", 4);
        }
        else
            pass = new Intent(ChatActivity.this, MyChatsActivity.class);
        pass.putExtra("currentUser", currentUser);
        pass.putExtra("blockedUsernames", getIntent().getStringArrayListExtra("blockedUsernames"));

        if ( currentChat.getChatID().indexOf(currentUser.getUsername()) == 0)
        {
            db.collection("chats").document(currentChat.getChatID()).update("readbyuser1", true);
        }
        else
        {
            db.collection("chats").document(currentChat.getChatID()).update("readbyuser2", true);
        }
        startActivity(pass);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}