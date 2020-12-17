package com.example.bookurbook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.Edits;
import android.os.Bundle;

import com.example.bookurbook.models.Admin;
import com.example.bookurbook.models.Chat;
import com.example.bookurbook.models.RegularUser;
import com.example.bookurbook.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Iterator;

public class MyChatsActivity extends AppCompatActivity {
    //variables
    private User currentUser;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    DocumentReference docRef;
    ArrayList<Chat> chatList;
    String otherUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_chats);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        if(getIntent().getSerializableExtra("user") instanceof Admin)
            currentUser = (Admin)getIntent().getSerializableExtra("user");
        else
            currentUser = (RegularUser)getIntent().getSerializableExtra("user");

        System.out.println("In chat, current user's email: " + currentUser.getEmail());
        db.collection("chats").addSnapshotListener(new EventListener<QuerySnapshot>()
        {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error)
            {
                if (error != null) {
                    System.out.println("Listen failed.");
                    return;
                }
                chatList = new ArrayList<Chat>();
                for (QueryDocumentSnapshot doc : value)
                {
                    if (doc != null && (doc.getString("username1").equals(currentUser.getUsername()) || doc.getString("username2").equals(currentUser.getUsername())))
                        {
                            if ( doc.getString("username1").equals(currentUser.getUsername()))
                                otherUsername = doc.getString("username2");
                            else
                                otherUsername = doc.getString("username1");
                            db.collection("users")
                                    .whereEqualTo("username", otherUsername)
                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                                    {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task)
                                        {
                                            if (task.isSuccessful())
                                            {
                                                for (DocumentSnapshot document : task.getResult())
                                                {
                                                    Chat chat = new Chat(currentUser, new RegularUser(document.getString("username"),
                                                            document.getString("email"), document.getString("avatar")));
                                                    chat.setLastMessageContentInDB(doc.getString("lastmessage"));
                                                    chatList.add(chat);
                                                }
                                            }
                                            else
                                            {
                                                System.out.println("hata");
                                            }
                                            for ( int i = 0; i < chatList.size(); i++ )
                                            {
                                                System.out.println("for: " + i);
                                                System.out.println(chatList.get(i).getUser1().getUsername());
                                                System.out.println(chatList.get(i).getUser2().getUsername());
                                            }
                                        }
                                    });
                    }
                }
            }
        });
    }
}
/*
     Adding time and sorting regarding time ( Melih )
     GUI ( Kaan or someone available )
*/