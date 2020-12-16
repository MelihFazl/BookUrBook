package com.example.bookurbook;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.models.Post;   // obje oluşturmak yerine import ettim ???
import com.example.models.PostList;
import com.example.models.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.Inflater;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostListViewHolder> implements Filterable {

    // variables
    private ArrayList<Post> postListHolder;
    private ArrayList<Post> postListHolderFull;
    PostList list;
    Context context;

    // constructor
    public PostListAdapter(Context c, PostList list)
    {
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
    public void onBindViewHolder(@NonNull PostListViewHolder postListViewHolder, int i) {

        Post examplePost = postListHolder.get(i);

        // postListViewHolder.image.setImageResource(examplePost.getPicture());   // whcih method did Kerem use for post
        postListViewHolder.title.setText(examplePost.getTitle());
        postListViewHolder.seller.setText(examplePost.getOwner().getUsername());      // whcih method did Kerem use for post, is this correct?
        postListViewHolder.price.setText(Integer.toString(examplePost.getPrice()));


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
        ImageView image;

        // constructor
        public PostListViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.postText);
            image = itemView.findViewById(R.id.postImageView);
            seller = itemView.findViewById(R.id.postSeller);
            price = itemView.findViewById(R.id.priceText);
        }
    }
}
