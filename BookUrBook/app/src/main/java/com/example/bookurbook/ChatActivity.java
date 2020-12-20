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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bookurbook.models.Admin;
import com.example.bookurbook.models.Chat;
import com.example.bookurbook.models.Message;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity implements ReportPostDialogListener
{
    //variables
    private User currentUser;
    private FirebaseFirestore db;
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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        db = FirebaseFirestore.getInstance();
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

                    Collections.sort(messages);
                    buildRecyclerView();

                    for ( int i = 0; i < messages.size(); i++ )
                    {
                        System.out.println("for: " + i);
                        System.out.println(messages.get(i).getContent());
                        System.out.println(messages.get(i).getMessageDate());
                        System.out.println(messages.get(i).getMessageTime());
                        System.out.println(messages.get(i).getSentBy());
                    }
                    Collections.sort(messages);
                    //Create gui by using messages
            }
        });


        //Message sending
        sendButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                date = new Date();
                Message message = new Message(currentUser.getUsername(), messageBox.getText().toString(), dateFormat.format(date), timeFormat.format(date));
                sendMessageToDatabase( message , date );
                messageBox.setText("");
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent startIntent = new Intent(ChatActivity.this, MainMenuActivity.class);
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
        //System.out.println(post.getReports().get(0).getDescription());
        //System.out.println(post.getReports().get(0).getCategory());
    }
}