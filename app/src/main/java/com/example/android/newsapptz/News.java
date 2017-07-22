package com.example.android.newsapptz;

/**
 * An {@link News} object contains information related to a single piece of news.
 */
public class News {

    /**
     * section of the news
     */
    private String mSection;

    /**
     * title of the news
     */
    private String mTitle;

    /**
     * url of the news
     */
    private String mNewsUrl;

    // constructor for new news object
    public News(String title, String section, String newsUrl) {

        mTitle = title;
        mSection = section;
        mNewsUrl = newsUrl;

    }

    /**
     * Returns the section of news.
     */
    public String getSection() {
        return mSection;
    }

    /**
     * Returns the title of news.
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Returns the url of news.
     */
    public String getNewsUrl() {
        return mNewsUrl;
    }

}