package com.example.bookurbook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

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

public class ChatActivity extends AppCompatActivity
{
    //variables
    private User currentUser;
    private FirebaseFirestore db;
    private CollectionReference msgRef;
    private String otherUsername;
    private Chat currentChat;
    private ArrayList<Message> messages;
    private Date date;
    private SimpleDateFormat dateFormat;
    private SimpleDateFormat timeFormat;

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
        timeFormat = new SimpleDateFormat("hh:mm");
        msgRef = db.collection("messages").document(currentChat.getChatID()).collection("messagetree");
        msgRef.addSnapshotListener(new EventListener<QuerySnapshot>()
        {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error)
            {
                if ( error != null)
                {
                    System.out.println("Listen failed.");
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
                    //Message sending
                    /* date = new Date();
                    Message message = new Message(currentUser.getUsername(), messageBox.getText(), dateFormat.format(date), timeFormat.format(date);
                    sendMessageToDatabase( message , date );
                     */
            }
        });
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
                db.collection("chats").document(currentChat.getUser1() + "," + currentChat.getUser2())
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
}