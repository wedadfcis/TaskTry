package com.example.wedad.tasktry.recycleradapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wedad.tasktry.R;
import com.example.wedad.tasktry.apicalls.MoviesData;

import java.util.List;

/**
 * Created by wedad on 11/18/2017.
 */

public class MovieAdpter extends RecyclerView.Adapter<MovieAdpter.MyViewHolder> {
     Context context;
     List<MoviesData>moviesList;

    public MovieAdpter(Context context, List<MoviesData> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.title.setText(moviesList.get(position).getTitle());
        holder.rating.setText("Rating "+moviesList.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        System.out.println("hello adpter"+moviesList.size());

        return moviesList.size();
    }

    ///---------------------View Holders ---------------------------//
    public  class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title,rating,type;

        public MyViewHolder(View itemView) {
            super(itemView);
            title=(TextView)itemView.findViewById(R.id.title);
            rating=(TextView)itemView.findViewById(R.id.rating);
            type=(TextView)itemView.findViewById(R.id.type);
        }

    }

}
