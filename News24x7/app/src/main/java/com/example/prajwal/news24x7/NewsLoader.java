package com.example.prajwal.news24x7;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import java.util.ArrayList;

public class NewsLoader extends AsyncTaskLoader<ArrayList<News>> {
    String mUrl;

    public NewsLoader(@NonNull Context context,String url) {
        super(context);
        mUrl=url;
    }

    @Override
    protected void onStartLoading() {

        forceLoad();
    }

    @Nullable
    @Override
    public ArrayList<News> loadInBackground() {
        ArrayList<News> news=Query.newsExtract(mUrl);
        return news;
    }
}
