package com.example.bookurbook;


import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookurbook.models.Post;

import com.example.bookurbook.models.PostList;
import com.example.bookurbook.models.User;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

import java.util.List;
;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostListViewHolder> implements Filterable {

    // variables
    private ArrayList<Post> postListHolder;
    private ArrayList<Post> postListHolderFull;
    private User postOwner;
    private User currentUser;
    private PostList postList;
    PostList list;
    Context context;

    // constructor
    public PostListAdapter(Context c, PostList list, User user)
    {
        currentUser = user;
        this.list = list;
        postListHolder = list.getPostArray(); // buraya bak
        postListHolderFull = new ArrayList<>(postListHolder);
        context = c;
    }


    @NonNull
    @Override
    public PostListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.post_row, viewGroup, false);
        return new PostListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostListViewHolder postListViewHolder, int i)
    {
        Post examplePost = postListHolder.get(i);
        postListViewHolder.title.setText(examplePost.getTitle());
        postListViewHolder.seller.setText(examplePost.getOwner().getUsername());      // whcih method did Kerem use for post, is this correct?
        postListViewHolder.price.setText(Integer.toString((int) examplePost.getPrice()) + "₺");
        Picasso.get().load(examplePost.getPicture()).into(postListViewHolder.picture);
        postListViewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(context, PostActivity.class);
                pass.putExtra("currentUser", currentUser);
                pass.putExtra("postlist", list);
                pass.putExtra("post", examplePost);
                pass.putExtra("previousActivity", 1);
                context.startActivity(pass);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postListHolder.size();
    }

    // for searching
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    public void sort(View v)                                    // changed here, TEST this
    {
        // will these work hmmmmmmmmmmmm ??  guess no ?
        // ArrayList<Post> filteredList = new ArrayList<>(list.getPostArray());
        PostList filteredList = list;


        if (v.getId() == R.id.AtoZ_button) {

            //list.sortByLetter(true);
            filteredList.sortByLetter(true);
        }
        else if (v.getId() == R.id.ZtoA_button) {
            //list.sortByLetter(false);
            filteredList.sortByLetter(false);
        }
        else if (v.getId() == R.id.LtoH_price_button) {
            //list.sortByPrice(true);
            filteredList.sortByPrice(true);

        }
        else if (v.getId() == R.id.HtoL_price_button) {
            //list.sortByPrice(false);
            filteredList.sortByPrice(false);

        }
        else if (v.getId() == R.id.reset_button) {                  // doesn't work after the first click !!!!!!
            //list.setPostArray(postListHolderFull);
            filteredList.setPostArray(postListHolderFull);
        }

        //postListHolder = new ArrayList<>(list.getPostArray());
        postListHolder = new ArrayList<>(filteredList.getPostArray());

        notifyDataSetChanged();
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Post> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0)
            {
                filteredList.addAll(postListHolderFull);
            }
            else
            {
                String filterInput = charSequence.toString().toLowerCase().trim();

                for (Post p : postListHolderFull)
                {
                    if (p.getTitle().toLowerCase().contains(filterInput))
                    {
                        filteredList.add(p);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            postListHolder.clear();
            postListHolder.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };



    // inner class for a view holder
    public class PostListViewHolder extends RecyclerView.ViewHolder {

        // variables
        TextView title;
        TextView seller;
        TextView price;
        ImageView picture;
        LinearLayout layout;

        // constructor
        public PostListViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.postText);
            picture = itemView.findViewById(R.id.postImageView);
            seller = itemView.findViewById(R.id.postSeller);
            price = itemView.findViewById(R.id.priceText);
            layout = (LinearLayout)  itemView.findViewById(R.id.row_post);
        }
    }
    public void filterResults(String uni, String course, int lowPrice, int highPrice)
    {
        PostList filteredList = list;

        if (!uni.equals("Other")) {
            filteredList = filteredList.filterByUniversity(uni);
        }
        if (!course.equals("Other")) {
            filteredList = filteredList.filterByCourse(course);

        }
        if (lowPrice != -1 || highPrice != -1) {
            filteredList = filteredList.filterByPrice(lowPrice, highPrice);

        }

        postListHolder = new ArrayList<>(filteredList.getPostArray());
        notifyDataSetChanged();
    }
}
