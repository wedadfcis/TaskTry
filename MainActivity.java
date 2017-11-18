package com.example.wedad.tasktry;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.wedad.tasktry.apicalls.ApiClient;
import com.example.wedad.tasktry.apicalls.ApiInterface;
import com.example.wedad.tasktry.apicalls.MoviesData;
import com.example.wedad.tasktry.recycleradapter.MovieAdpter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView myRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    MovieAdpter movieAdpter;
    ApiInterface apiInterface;
    ProgressBar progressBar;
    List<MoviesData> moviesList;
    int pageNumber = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        layoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(layoutManager);
        moviesList = new ArrayList<>();
        movieAdpter = new MovieAdpter(getApplicationContext(), moviesList);
        myRecyclerView.setAdapter(movieAdpter);
        ///-----------------------------------pull to refresh --------------------------------------------------//
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isOnline() == true) {
                            clear();
                            FetchData(1);
                            // cancle the Visual indication of a refresh
                            swipeRefreshLayout.setRefreshing(false);
                        } else {
                            Toast.makeText(getApplicationContext(), "check your internet connection", Toast.LENGTH_LONG).show();
                        }

                    }
                }, 3000);
            }
        });
        ///------------------------------------endless scroll ---------------------------------------------------//
        myRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener((LinearLayoutManager) layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                pageNumber++;
                checkConnection(pageNumber);

            }
        });
        checkConnection(pageNumber);
    }

    //--------------------------------------check connection ----------------------------------------------------//
    void checkConnection(int pageNum) {
        if (isOnline() == true) {
            FetchData(pageNum);
        } else {
            Toast.makeText(getApplicationContext(), "check your internet connection", Toast.LENGTH_LONG).show();
        }
    }

    //-------------------------------------clear data -----------------------------------------------------------//
    public void clear() {
        int size = moviesList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                moviesList.remove(0);
            }

            movieAdpter.notifyItemRangeRemoved(0, size);
        }
    }

    //--------------------------------------fetch data from api --------------------------------------------------//
    private void FetchData(int page) {

        // Set up progress before call
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();

        apiInterface = ApiClient.getRetrfitInstance().create(ApiInterface.class);
        Call<List<MoviesData>> call = apiInterface.getListOfMovies(page);
        call.enqueue(new Callback<List<MoviesData>>() {
            @Override
            public void onResponse(Call<List<MoviesData>> call, Response<List<MoviesData>> response) {
                System.out.println("response" + response.body());
                moviesList.addAll(response.body());
                System.out.println(moviesList.size());
                System.out.println(moviesList.get(0).getTitle());
                movieAdpter.notifyItemRangeChanged(movieAdpter.getItemCount(), moviesList.size() - 1);
                System.out.println("hello");
                swipeRefreshLayout.setRefreshing(false);

                if (mProgressDialog.isShowing())
                    mProgressDialog.dismiss();


            }

            @Override
            public void onFailure(Call<List<MoviesData>> call, Throwable t) {
                System.out.println("Error" + t.toString());
                if (isOnline() == false) {
                    Toast.makeText(getApplicationContext(), "check your internet connection", Toast.LENGTH_LONG).show();
                }
                swipeRefreshLayout.setRefreshing(false);
                mProgressDialog.dismiss();
            }
        });

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
