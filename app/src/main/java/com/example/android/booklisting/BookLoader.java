package com.example.android.booklisting;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<Book>> {
    /**Tag for log messages*/
    private static final String LOG_TAG = BookLoader.class.getName();

    /**Query URL*/
    private String mUrl;

    /**
     * Constructs a new {@link BookLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading()
    {
        Log.i(LOG_TAG,"Method onStartLoading created");
        forceLoad();
    }

    /**
     * This is on background thread.
     */
    @Override
    public List<Book> loadInBackground() {
        Log.i(LOG_TAG,"Method onStartLoading created");
        if (mUrl == null) {
            return null;
        }

        //Perform the network request, parse the response, and extract a list of earthquakes.
        List<Book> books = QueryUtils.fetchBookData(mUrl);
        return books;
    }
}
