package com.example.android.newsapptz;


import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<News>> {

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;

    /**
     * Constant value for the news loader ID.
     */
    private static final int NEWS_LOADER_ID = 1;

    /**
     * URL for news from Guardian
     */
    private static final String NEWS_REQUEST_URL =
            "https://content.guardianapis.com/search?q=technology&api-key=test";

    /**
     * Adapter for the list of news
     */
    private NewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // empty state text view
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);

        // Find a reference to the {@link ListView} in the layout
        ListView newsListView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        newsListView.setEmptyView(mEmptyStateTextView);

        // Create a new adapter that takes an empty list of news as input
        mAdapter = new NewsAdapter(this, new ArrayList<News>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        newsListView.setAdapter(mAdapter);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

        // we set a listener on each item - when clicked it directs us to
        // this specific piece of news webpage (url from JSON response is used)
        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                News news = mAdapter.getItem(position);

                // url form JSON response
                Uri uriURL = Uri.parse(news.getNewsUrl());

                // intent to browser
                Intent webIntent = new Intent(Intent.ACTION_VIEW, uriURL);
                startActivity(webIntent);
            }
        });


    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new NewsLoader(this, NEWS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newses) {

        // Set empty state text to inform that the list is empty
        mEmptyStateTextView.setText(R.string.no_news);

        // Clear the adapter of previous news
        mAdapter.clear();

        // If there is a valid list of {@link News}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (newses != null && !newses.isEmpty()) {
            mAdapter.addAll(newses);
        }
    }


    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}
