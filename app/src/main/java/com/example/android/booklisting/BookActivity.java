/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.booklisting;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity implements LoaderCallbacks<List<Book>> {

    private static final String LOG_TAG = BookActivity.class.getName();
    /** URL for earthquake data from the USGS dataset */
        private static final String USGS_REQUEST_URL =
            "https://www.googleapis.com/books/v1/volumes";
        //?q=android&maxResults=5"


    /** Adapter for the list of books */
    private BookAdapter mAdapter;

    /**
     * Constant value for the book loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int BOOK_LOADER_ID = 1;

    /**TextView displayed when the ListView is empty*/
    private TextView mEmptyStateTextView;

    /**The Loading indicator*/
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_activity);

        progressBar.setVisibility(View.GONE);

        Button loadButton = findViewById(R.id.load_button);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Find a reference to the {@link ListView} in the layout
                ListView bookListView = findViewById(R.id.list);

                // Create a new adapter that takes an empty list of earthquakes as input
                mAdapter = new BookAdapter(BookActivity.this, new ArrayList<Book>());

                // Set the adapter on the {@link ListView}
                // so the list can be populated in the user interface
                bookListView.setAdapter(mAdapter);

                mEmptyStateTextView = findViewById(R.id.empty_view);
                bookListView.setEmptyView(mEmptyStateTextView);

                progressBar = findViewById(R.id.progress);

                //Get a reference to the Loader Manager, in order to interact with loaders.
                LoaderManager loaderManager = getLoaderManager();


                    ConnectivityManager connectivityManager = (ConnectivityManager)
                            getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {

                        //Initialise the loader. Pass in the int ID constant defined above and pass in null for
                        //bundle. Pass in this activity for the LoaderCallbacks parameter ((which is valid)
                        //because ths activity implements the LoaderCallbacks interface.
                        loaderManager.initLoader(BOOK_LOADER_ID, null, BookActivity.this);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        mEmptyStateTextView.setText(R.string.no_internet);
                    }
            }
        });




    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {

        EditText keywordInput = findViewById(R.id.search);
        String keyword = keywordInput.getText().toString();

        Uri baseUri = Uri.parse(USGS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        //uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("maxResults", "10");
        uriBuilder.appendQueryParameter("q", keyword);

        //Create a new loader for the given URL
        return new BookLoader(this, uriBuilder.toString());

    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        //Clear the adapter of previous earthquake data
        mAdapter.clear();


        //progressBar.setVisibility(View.GONE);

        //If there is a valid list of {@link Book}, then add them to the adapter's data set.
        //This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
        }

        @Override
        public void onLoaderReset(Loader<List<Book>> loader) {
            // Loader reset, so we can clear out existing data
            mAdapter.clear();
        }

   }
