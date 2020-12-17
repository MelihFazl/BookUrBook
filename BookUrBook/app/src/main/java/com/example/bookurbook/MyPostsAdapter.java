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
        private ImageView soldPhoto, tickSold;
        private LinearLayout layout;
        private ImageButton editButton;;


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
            this.tickSold = view.findViewById(R.id.sold_tick);
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

        holder.tickSold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirm");
                if (!myPosts.get(position).isSold()) {
                    builder.setMessage("Are you sure that you want to mark the Post as sold?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            myPosts.get(position).setSold(true);
                            holder.soldPhoto.setImageResource(R.drawable.sold);
                            dialog.dismiss();
                            Toast.makeText(context, "You have successfully sold your Post!", Toast.LENGTH_SHORT).show();
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
                else
                {
                    builder.setMessage("Are you sure that you want to mark the Post as unsold?");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            myPosts.get(position).setSold(false);
                            holder.soldPhoto.setVisibility(View.INVISIBLE);
                            dialog.dismiss();
                            Toast.makeText(context, "You have marked your Post as unsold!", Toast.LENGTH_SHORT).show();
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
            }
        });


    }



    @Override
    public int getItemCount() {
        return myPosts.size();
    }
}
