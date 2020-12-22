package com.example.bookurbook;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookurbook.models.Post;
import com.example.bookurbook.models.PostList;
import com.example.bookurbook.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class WishlistAdapter extends RecyclerView.Adapter<WishlistAdapter.ViewHolder>{
    private ArrayList<Post> posts;
    private User currentUser;
    private Context context;


    public WishlistAdapter(Context context, ArrayList<Post> posts, User currentUser) {
        this.posts = posts;
        this.context = context;
        this.currentUser = currentUser;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //inner class properties
        private TextView postName, seller, price;
        private ImageView photo, likeButton;
        private LinearLayout layout;

        //inner class constructor
        public ViewHolder(View view) {
            super(view);
            this.postName = view.findViewById(R.id.postText);
            this.seller = view.findViewById(R.id.postSeller);
            this.price = view.findViewById(R.id.priceText);
            this.photo = view.findViewById(R.id.postImageView);
            this.likeButton = view.findViewById(R.id.like_btn);
            this.layout = view.findViewById(R.id.wishlist_layout);
        }
    }

    @NonNull
    @Override
    public WishlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_wishlist, parent, false);
        return new WishlistAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.postName.setText(posts.get(position).getTitle());
        holder.seller.setText(posts.get(position).getOwner().getUsername());
        Picasso.get().load(posts.get(position).getPicture()).into(holder.photo);
        holder.price.setText(Integer.toString(posts.get(position).getPrice()) + "₺");
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PostActivity.class);
                intent.putExtra("post", posts.get(position));
                intent.putExtra("currentUser", posts.get(position).getOwner().getUsername());
                intent.putExtra("fromPostList",false);
                PostList postlist = new PostList();
                for(int i = 0; i < posts.size(); i++)
                {
                    postlist.addPost(posts.get(i));
                }
                intent.putExtra("postlist", postlist);
                context.startActivity(intent);
            }
        });
        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure that you want to remove " + posts.get(position).getTitle() + " from your Wish List?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, posts.get(position).getTitle() + " has been removed from WishList", Toast.LENGTH_SHORT).show();
                        currentUser.getWishList().remove(posts.get(position));
                        Intent pass = new Intent(context, WishlistActivity.class);
                        pass.putExtra("currentUser", currentUser);
                        context.startActivity(pass);
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
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
        return posts.size();
    }
}
