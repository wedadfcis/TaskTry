package com.example.wedad.tasktry.apicalls;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by wedad on 11/17/2017.
 */

public interface ApiInterface {

    @GET("movies.php")
    Call<List<MoviesData>> getListOfMovies(@Query("index")int index);
}
