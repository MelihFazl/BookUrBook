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
import com.example.bookurbook.models.User;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.ViewHolder>{
    private ArrayList<User> reportedUsers;
    private User user;
    private Context context;


    public ReportsAdapter(Context context, ArrayList<User> users) {
        this.reportedUsers = users;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //inner class properties
        private TextView username, reportNumber;
        private ImageView photo, bannedView;
        private ImageButton bannedButton;

        //inner class constructor
        public ViewHolder(View view) {
            super(view);
            this.username = view.findViewById(R.id.blocked_username);
            this.photo = view.findViewById(R.id.image_user);
            this.bannedButton = view.findViewById(R.id.btn_ban);
            this.bannedView =view.findViewById(R.id.bannedPhoto);
            this.reportNumber = view.findViewById(R.id.report_number);
        }
    }


    @NonNull
    @Override
    public ReportsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_report, parent, false);
        return new ReportsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportsAdapter.ViewHolder holder, int position) {
        holder.username.setText(reportedUsers.get(position).getUsername());
        Picasso.get().load(reportedUsers.get(position).getAvatar()).into(holder.photo);
        holder.reportNumber.setText((reportedUsers.get(position).getReportNum() + ""));
        holder.bannedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(reportedUsers.get(position).isBanned()) {
                    reportedUsers.get(position).setBanned(false);
                    holder.bannedView.setVisibility(View.INVISIBLE);
                    Toast.makeText(context, reportedUsers.get(position).getUsername() + "'s ban has been removed", Toast.LENGTH_SHORT).show();
                }
                else {
                    reportedUsers.get(position).setBanned(true);
                    holder.bannedView.setVisibility(View.VISIBLE);
                    Toast.makeText(context, reportedUsers.get(position).getUsername() + " has been banned", Toast.LENGTH_SHORT).show();
                }
                    }
                });

            }

    @Override
    public int getItemCount() {
        return reportedUsers.size();
    }

}
