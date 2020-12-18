package com.example.bookurbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookurbook.models.Chat;
import com.example.bookurbook.models.PostList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyChatsAdapter extends RecyclerView.Adapter<MyChatsAdapter.MyChatsViewHolder> {

    // variables
    ArrayList<Chat> chatsList;
    Context context;

    public MyChatsAdapter(Context c, ArrayList<Chat> list)
    {
        chatsList = new ArrayList<>(list);
        context = c;
    }


    @NonNull
    @Override
    public MyChatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.chat_view, parent, false);
        return new MyChatsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyChatsViewHolder holder, int position) {

        Chat exampleChat = chatsList.get(position);
        holder.userName.setText(exampleChat.getUser2().getUsername());
        holder.latestChat.setText(exampleChat.getLastMessage().getContent());  // maybe this will be getLastMessageInFromDb ??
        Picasso.get().load(exampleChat.getUser2().getAvatar()).into(holder.userAvatar);

    }

    @Override
    public int getItemCount() {
        return chatsList.size();
    }

    public class MyChatsViewHolder extends RecyclerView.ViewHolder {

        // variables
        ImageView userAvatar;
        TextView userName;
        TextView latestChat;

        public MyChatsViewHolder(@NonNull View itemView) {
            super(itemView);
            userAvatar = itemView.findViewById(R.id.my_chats_avatar);
            userName = itemView.findViewById(R.id.my_chats_username);
            latestChat = itemView.findViewById(R.id.my_chats_latest_message);
        }
    }
}
