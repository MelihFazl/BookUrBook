package com.example.bookurbook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
        private ImageButton unblockButton;

        //inner class constructor
        public ViewHolder(View view) {
            super(view);
            this.username = view.findViewById(R.id.blocked_username);
            this.photo = view.findViewById(R.id.image_user);
            this.unblockButton = view.findViewById(R.id.btn_unblock);
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
        //holder.photo.setImageResource(blockedUsers.get(position).getAvatar());
        holder.unblockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure that you want to unblock " + blockedUsers.get(position).getUsername() + "?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Toast.makeText(context, blockedUsers.get(position).getUsername() + "'s block has been removed", Toast.LENGTH_SHORT).show();
                        user.getBlockedUsers().deleteUser(blockedUsers.get(position));
                        context.startActivity(new Intent(context, MyBlockListActivity.class));
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }
    @Override
    public int getItemCount() {
        return blockedUsers.size();
    }

}
