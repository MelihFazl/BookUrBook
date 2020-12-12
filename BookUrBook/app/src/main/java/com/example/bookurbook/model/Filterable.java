package com.example.bookurbook.model;

public interface Filterable
{
    PostList filterByUniversity(String University);

    PostList filterByCourse(String course);

    PostList filterByPrice(int min, int max);

    PostList filterByOwner(User owner);

}
