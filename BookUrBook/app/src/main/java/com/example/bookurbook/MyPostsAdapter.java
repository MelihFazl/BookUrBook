package com.example.bookurbook;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookurbook.R;
import com.example.bookurbook.models.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyPostsAdapter extends RecyclerView.Adapter<MyPostsAdapter.ViewHolder> {

    //properties
    private ArrayList<Post> myPosts;
    private Context context;


    public MyPostsAdapter(Context context, ArrayList<Post> posts) {
        myPosts = posts;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //inner class properties
        private TextView title, seller, price;
        private ImageView photo;
        private ImageView soldPhoto;
        private LinearLayout layout;
        private ImageButton editButton;


        //inner class constructor
        public ViewHolder(View view) {
            super(view);
            this.title = view.findViewById(R.id.postText);
            this.seller = view.findViewById(R.id.postSeller);
            this.price = view.findViewById(R.id.priceText);
            this.photo = view.findViewById(R.id.postImageView);
            this.soldPhoto = view.findViewById(R.id.soldView);
            this.layout = (LinearLayout) view.findViewById(R.id.singlePostLayout);
            this.editButton = view.findViewById(R.id.edit_button);
        }
    }

    //constructor


    @NonNull
    @Override
    public MyPostsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.posts_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPostsAdapter.ViewHolder holder, int position) {
        holder.title.setText(myPosts.get(position).getTitle());
        holder.seller.setText(myPosts.get(position).getOwner().getUsername());
        Picasso.get().load(myPosts.get(position).getPicture()).into(holder.photo);
        holder.price.setText(Integer.toString(myPosts.get(position).getPrice()) + "₺");
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PostActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("post", myPosts.get(position));
                context.startActivity(intent);

            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View view) {
                Intent intent2 = new Intent(context, EditPostActivity.class);
                intent2.putExtra("post", myPosts.get(position));
                context.startActivity(intent2);
            }
        });
        if(myPosts.get(position).isSold())
           holder.soldPhoto.setVisibility(View.VISIBLE);
        else
           holder.soldPhoto.setVisibility(View.INVISIBLE);

    }

    @Override
    public int getItemCount() {
        return myPosts.size();
    }
}
