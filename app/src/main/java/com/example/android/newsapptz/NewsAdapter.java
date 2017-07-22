package com.example.android.newsapptz;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * An {@link NewsAdapter} knows how to create a list item layout for each piece of news
 * in the data source (a list of {@link News} objects).
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class NewsAdapter extends ArrayAdapter<News> {

    /**
     * Constructs a new {@link NewsAdapter}.
     *
     * @param context of the app
     * @param newses  is the list of newses, which is the data source of the adapter
     */
    public NewsAdapter(Context context, List<News> newses) {
        super(context, 0, newses);
    }

    /**
     * Returns a list item view that displays information about the news at the given position
     * in the list of newses.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Find the news at the given position in the list of newses
        News currentNews = getItem(position);

        // Get the title string from the news object
        String title = currentNews.getTitle();

        // Get the section string from the news object
        String section = currentNews.getSection();

        // Find the TextView with view ID title_text
        TextView titleView = (TextView) listItemView.findViewById(R.id.title_text);

        // Display the title of the current news in that TextView
        titleView.setText("Title: " + title);

        // Find the TextView with view ID section_text
        TextView sectionView = (TextView) listItemView.findViewById(R.id.section_text);

        // Display the section of the current news in that TextView
        sectionView.setText("Section: " + section);

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }

}