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

import com.example.bookurbook.models.Post;
import com.example.bookurbook.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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
        private ImageView photo;

        //inner class constructor
        public ViewHolder(View view) {
            super(view);
            this.postName = view.findViewById(R.id.postText);
            this.seller = view.findViewById(R.id.postSeller);
            this.price = view.findViewById(R.id.priceText);
            this.photo = view.findViewById(R.id.postImageView);
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
        holder.seller.setText(posts.get(position).getOwner().toString());
        Picasso.get().load(posts.get(position).getPicture()).into(holder.photo);
        holder.price.setText(Integer.toString(posts.get(position).getPrice()) + "₺");

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
