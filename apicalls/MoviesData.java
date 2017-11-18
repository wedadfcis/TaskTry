package com.example.wedad.tasktry.apicalls;

import com.google.gson.annotations.SerializedName;

/**
 * Created by wedad on 11/17/2017.
 */

public class MoviesData {
    @SerializedName("title")
    private String title;
    @SerializedName("rating")
    private double rating;
    @SerializedName("type")
    private String type;

    public String getTitle() {
        return title;
    }

    public double getRating() {
        return rating;
    }

    public String getType() {
        return type;
    }
}
