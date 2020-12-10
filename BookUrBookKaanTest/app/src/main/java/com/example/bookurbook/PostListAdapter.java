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
    Context context;

    // constructor
    public PostListAdapter(Context c, ArrayList<Post> list)
    {
        postListHolder = list;
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

        postListViewHolder.image.setImageResource(examplePost.getImage());
        postListViewHolder.title.setText(examplePost.getDescription());
        postListViewHolder.seller.setText(examplePost.getSeller());
        postListViewHolder.price.setText(examplePost.getPrice());


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

    public void sort(View v)               // used COMPARABLE
    {
        ArrayList<Post> filteredList = new ArrayList<>(postListHolderFull);
        if (v.getId() == R.id.AtoZ_button) {
            Collections.sort(filteredList, Post.Comparators.descAtoZ);
        }
        else if (v.getId() == R.id.ZtoA_button) {
            Collections.sort(filteredList, Post.Comparators.descZtoA);
        }
        else if (v.getId() == R.id.LtoH_price_button) {
            Collections.sort(filteredList, Post.Comparators.priceLtoH);
        }
        else if (v.getId() == R.id.HtoL_price_button) {
            Collections.sort(filteredList, Post.Comparators.priceHtoL);
        }
        else if (v.getId() == R.id.reset_button) {
            filteredList = new ArrayList<>(postListHolderFull);
        }

        postListHolder = new ArrayList<>(filteredList);
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
                    if (p.getDescription().toLowerCase().contains(filterInput))
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
