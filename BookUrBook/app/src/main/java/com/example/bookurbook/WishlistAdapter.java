package com.example.bookurbook;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookurbook.models.Post;
import com.example.bookurbook.models.PostList;
import com.example.bookurbook.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
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
    private FirebaseFirestore db;
    private FirebaseAuth auth;


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
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        holder.postName.setText(posts.get(position).getTitle());
        holder.seller.setText(posts.get(position).getOwner().getUsername());
        Picasso.get().load(posts.get(position).getPicture()).into(holder.photo);
        holder.price.setText(Integer.toString(posts.get(position).getPrice()) + "₺");
        holder.likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("users").document(auth.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        List<String> wishlist = Collections.emptyList();
                        wishlist = (List<String>) documentSnapshot.get("wishlist");
                        wishlist.remove(posts.get(position).getId());
                        HashMap<String, Object> newData = new HashMap<>();
                        newData.put("wishlist", wishlist);
                        db.collection("users").document(auth.getCurrentUser().getUid()).set(newData, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid)
                            {
                                Toast.makeText(context, posts.get(position).getTitle() + " has been removed from your WishList", Toast.LENGTH_SHORT).show();
                                currentUser.getWishList().remove(posts.get(position));
                                Intent pass = new Intent(context, WishlistActivity.class);
                                pass.putExtra("currentUser", currentUser);
                                context.startActivity(pass);
                            }
                        });
                    }
                });
            }
        });
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PostActivity.class);
                intent.putExtra("post", posts.get(position));
                intent.putExtra("currentUser", currentUser);
                intent.putExtra("previousActivity", 3);
                PostList postlist = new PostList();
                for(int i = 0; i < posts.size(); i++)
                {
                    postlist.addPost(posts.get(i));
                }
                intent.putExtra("postlist", postlist);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
