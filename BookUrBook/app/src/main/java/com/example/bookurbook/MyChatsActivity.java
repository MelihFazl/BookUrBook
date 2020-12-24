package com.example.bookurbook;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.text.Edits;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MyChatsActivity extends AppCompatActivity {
    //variables
    private User currentUser;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private DocumentReference docRef;
    private ArrayList<Chat> chatList;
    private String otherUsername;
    private RecyclerView recyclerView;
    private MyChatsAdapter myChatsAdapter;
    private ArrayList<String> blockedUsernames;
    private Toolbar toolbar;
    private int otherIs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_chats);

        toolbar = findViewById(R.id.toolbar_my_chats);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        chatList = new ArrayList<Chat>();
        buildRecyclerView();

        if(getIntent().getSerializableExtra("currentUser") instanceof Admin)
            currentUser = (Admin)getIntent().getSerializableExtra("currentUser");
        else
            currentUser = (RegularUser)getIntent().getSerializableExtra("currentUser");

        blockedUsernames = getIntent().getStringArrayListExtra("blockedUsernames");
        db.collection("chats").addSnapshotListener(new EventListener<QuerySnapshot>()
        {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error)
            {
                if (error != null) {
                    Toast chatError = Toast.makeText(MyChatsActivity.this,"Something is wrong. Please check your Internet connection.", Toast.LENGTH_LONG);
                    chatError.show();
                    return;
                }
                chatList = new ArrayList<Chat>();
                for (QueryDocumentSnapshot doc : value)
                {
                    if (doc != null && (doc.getString("username1").equals(currentUser.getUsername()) || doc.getString("username2").equals(currentUser.getUsername())))
                        {
                            if ( doc.getString("username1").equals(currentUser.getUsername()))
                            {
                                otherUsername = doc.getString("username2");
                                System.out.println(otherUsername + ": 2");
                                otherIs = 2;
                            }
                            else
                            {
                                otherUsername = doc.getString("username1");
                                System.out.println(otherUsername + ": 1");
                                otherIs = 1;
                            }
                            db.collection("users").whereEqualTo("username", otherUsername)
                                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                            {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task)
                                {
                                    if (task.isSuccessful())
                                    {
                                        for (DocumentSnapshot document : task.getResult())
                                        {
                                           if( !blockedUsernames.contains(document.getString("username")))
                                           {
                                                Chat chat = new Chat(currentUser, new RegularUser(document.getString("username"),
                                                        document.getString("email"), document.getString("avatar")), doc.getId());
                                                chat.setLastMessageContentInDB(doc.getString("lastmessage"));
                                                chat.setDate(doc.getDate("lastmessagedate"));
                                                if ( otherIs == 2 )
                                                {
                                                    System.out.println(chat.getUser2().getUsername() + "-" + doc.get("readbyuser1"));
                                                    chat.setReadByUser1(doc.getBoolean("readbyuser1"));
                                                    chat.setReadByUser2(doc.getBoolean("readbyuser2"));
                                                }
                                                else if( otherIs == 1 )
                                                {
                                                    System.out.println(chat.getUser2().getUsername() + "-" + doc.get("readbyuser2"));
                                                    chat.setReadByUser1(doc.getBoolean("readbyuser2"));
                                                    chat.setReadByUser2(doc.getBoolean("readbyuser1"));
                                                }
                                                chatList.add(chat);
                                           }
                                        }
                                    }
                                    else
                                    {
                                        Toast chatError = Toast.makeText(MyChatsActivity.this,"Something is wrong. Please check your Internet connection.", Toast.LENGTH_LONG);
                                        chatError.show();
                                    }
                                    //Update GUI
                                    Collections.sort(chatList);
                                    buildRecyclerView();
                                }
                            });
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        Intent pass = new Intent(MyChatsActivity.this, MainMenuActivity.class);
        pass.putExtra("currentUser", currentUser);
        startActivity(pass);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void buildRecyclerView()
    {
        recyclerView = findViewById(R.id.my_chats_recycler_id);
        myChatsAdapter = new MyChatsAdapter(MyChatsActivity.this, chatList, currentUser, blockedUsernames);
        recyclerView.setAdapter(myChatsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyChatsActivity.this));
        myChatsAdapter.notifyDataSetChanged();
    }
}
