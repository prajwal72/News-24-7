package com.example.prajwal.news24x7;

public class News {
    private String mSource;
    private String mTitle;
    private String mUrl;
    private String mImageUrl;
    private String mDescription;
    private String mDate;
    private String mContent;

    public News(String source,String title, String description, String url, String imageUrl,String date,String content){
        mContent=content;
        mDate=date;
        mSource=source;
        mTitle=title;
        mUrl=url;
        mDescription=description;
        mImageUrl=imageUrl;
    }

    public String getmUrl() {
        return mUrl;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmContent() {
        return mContent;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public String getmSource() {
        return mSource;
    }

    public String getmTitle() {
        return mTitle;
    }
}
