package com.example.bookurbook;

import java.util.Comparator;

public class Post implements Comparable<Post>{

    // properties
    private int image;
    private String seller;
    private String description;
    private String price;
    private String uni;
    private String course;

    // constructor
    public Post(int img, String desc, String seller, String price, String uni, String course)
    {
        image = img;
        description = desc;
        this.seller = seller;
        this.price = price + "₺";
        this.uni = uni;
        this.course = course;

    }

    // getters
    public int getImage() {
        return image;
    }
    public String getDescription() {
        return description;
    }
    public String getSeller() {
        return seller;
    }
    public String getPrice() {
        return price;
    }
    public String getUni() {
        return uni;
    }
    public String getCourse() {
        return course;
    }

    @Override
    public int compareTo(Post post) {
        return 0;
    }

    public static class Comparators
    {
        public static Comparator<Post> descAtoZ = new Comparator<Post>() {
            @Override
            public int compare(Post post, Post t1) {
                return post.description.compareTo(t1.description);
            }
        };
        public static Comparator<Post> descZtoA = new Comparator<Post>() {
            @Override
            public int compare(Post post, Post t1) {
                return t1.description.compareTo(post.description);
            }
        };
        public static Comparator<Post> priceLtoH = new Comparator<Post>() {
            @Override
            public int compare(Post post, Post t1) {
                return Integer.parseInt(post.price.substring(0, post.price.length()-1)) - Integer.parseInt(t1.price.substring(0, t1.price.length()-1));
            }
        };
        public static Comparator<Post> priceHtoL = new Comparator<Post>() {
            @Override
            public int compare(Post post, Post t1) {
                return Integer.parseInt(t1.price.substring(0, t1.price.length()-1)) - Integer.parseInt(post.price.substring(0, post.price.length()-1));
            }
        };


    }



}
