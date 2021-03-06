package com.example.prajwal.news24x7;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements LoaderCallbacks<ArrayList<News>> {
    static final String NEWS_URL="https://newsapi.org/v2/top-headlines";
    static final String API_KEY ="c47ec8dc2c194918b29d8d85e072f4d5";
    private String category;
    private DrawerLayout mDrawerLayout;
    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private View loadingIndicator;
    private TextView emptyView;
    private Toolbar toolbar;
    private Button tryAgain;
    private NavigationView navigationView;
    int flag = 0; //Checks id loader has been initialised

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        toolbar.setTitle(R.string.general);
        mLayoutManager = new LinearLayoutManager(this);
        category = "General";
        emptyView = (TextView)findViewById(R.id.empty_view);
        loadingIndicator = findViewById(R.id.loading);
        tryAgain = (Button)findViewById(R.id.try_again);
        navigationView = findViewById(R.id.nav_view);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white);


        if(checkNetwork()){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(0,null,this);
            flag = 1;
        }
        else
        {
            loadingIndicator.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            tryAgain.setVisibility(View.VISIBLE);
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }

        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkNetwork()){
                    recyclerView.setVisibility(View.VISIBLE);
                    emptyView.setVisibility(View.INVISIBLE);
                    tryAgain.setVisibility(View.INVISIBLE);
                    loadingIndicator.setVisibility(View.VISIBLE);
                    if(flag == 0){
                        LoaderManager loaderManager = getLoaderManager();
                        loaderManager.initLoader(0,null,MainActivity.this);
                        flag = 1;
                    }
                    else
                    {
                        getLoaderManager().restartLoader(0,null,MainActivity.this);
                    }

                }
            }
        });

        navigationView.getMenu().getItem(2).setChecked(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                int id = menuItem.getItemId();
                loadingIndicator.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                if(checkNetwork() == false){
                    emptyView.setVisibility(View.VISIBLE);
                    tryAgain.setVisibility(View.VISIBLE);
                }
                switch (id){
                    case R.id.sports:category = "sports";
                        getLoaderManager().restartLoader(0,null,MainActivity.this);
                        toolbar.setTitle(R.string.sports);
                        break;

                    case R.id.general:category = "general";
                        getLoaderManager().restartLoader(0,null,MainActivity.this);
                        toolbar.setTitle(R.string.general);
                        break;

                    case R.id.business:category = "business";
                        getLoaderManager().restartLoader(0,null,MainActivity.this);
                        toolbar.setTitle(R.string.business);
                        break;

                    case R.id.entertainment:category = "entertainment";
                        getLoaderManager().restartLoader(0,null,MainActivity.this);
                        toolbar.setTitle(R.string.entertainment);
                        break;

                    case R.id.science:category = "science";
                        getLoaderManager().restartLoader(0,null,MainActivity.this);
                        toolbar.setTitle(R.string.science);
                        break;

                    case R.id.technology:category = "technology";
                        getLoaderManager().restartLoader(0,null,MainActivity.this);
                        toolbar.setTitle(R.string.technology);
                        break;

                    case R.id.health:category = "health";
                        getLoaderManager().restartLoader(0,null,MainActivity.this);
                        toolbar.setTitle(R.string.health);
                        break;

                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void UpdateUI(final ArrayList<News> news){
        adapter = new NewsAdapter(this, news);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

    }


    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, Bundle args) {
        Uri baseUri = Uri.parse(NEWS_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("country", "in");
        uriBuilder.appendQueryParameter("pageSize","100");
        uriBuilder.appendQueryParameter("apiKey", API_KEY);
        uriBuilder.appendQueryParameter("category", category);
        return new NewsLoader(this,uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<News>> loader, ArrayList<News> data) {
        loadingIndicator = findViewById(R.id.loading);
        loadingIndicator.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        UpdateUI(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<News>> loader) {

    }

    public boolean checkNetwork(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo!=null && networkInfo.isConnected());
    }

}
