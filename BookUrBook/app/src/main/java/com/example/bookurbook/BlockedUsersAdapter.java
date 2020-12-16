package com.example.bookurbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookurbook.R;

import com.example.bookurbook.models.User;

import java.util.ArrayList;

public class BlockedUsersAdapter extends RecyclerView.Adapter<BlockedUsersAdapter.ViewHolder>{
    //properties
    private ArrayList<User> blockedUsers;
    private User user;
    private Context context;


    public BlockedUsersAdapter(Context context, ArrayList<User> users, User currentUser) {
        this.blockedUsers = users;
        this.context = context;
        this.user = currentUser;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //inner class properties
        private TextView username;
        private ImageView photo;
        private ImageButton blockButton;

        //inner class constructor
        public ViewHolder(View view) {
            super(view);
            this.username = view.findViewById(R.id.blocked_username);
            this.photo = view.findViewById(R.id.image_user);
            this.blockButton = view.findViewById(R.id.btn_block);
        }
    }


    @NonNull
    @Override
    public BlockedUsersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_blocklist, parent, false);
        return new BlockedUsersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BlockedUsersAdapter.ViewHolder holder, int position) {
        holder.username.setText(blockedUsers.get(position).getUsername());
        holder.photo.setImageResource(blockedUsers.get(position).getAvatar());
        holder.blockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, blockedUsers.get(position).getUsername() + "'s block has been removed", Toast.LENGTH_SHORT).show();
                user.getBlockedUsers().deleteUser(blockedUsers.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return blockedUsers.size();
    }

}
