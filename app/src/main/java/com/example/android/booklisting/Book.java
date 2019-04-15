package com.example.android.booklisting;


import android.app.Activity;

import org.json.JSONArray;

import java.lang.reflect.Array;

public class Book {

    /**Title of the Book*/
    private String mTitle;

    /**Author of the Book*/
    private String mAuthor;

    public Book(String title, String author){

        mTitle = title;
        mAuthor = author;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }


}
