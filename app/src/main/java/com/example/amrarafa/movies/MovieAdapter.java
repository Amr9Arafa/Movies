package com.example.amrarafa.movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by amr arafa on 2/17/2016.
 */

public class MovieAdapter extends ArrayAdapter<MovieDetails> {

    public MovieAdapter(Context context, ArrayList<MovieDetails> movieDetails) {
        super(context, 0 ,movieDetails);
        // TODO Auto-generated constructor stub
    }

    public MovieAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View recycled, ViewGroup container) {
        final ImageView myImageView;
        Context context;
        if (recycled == null) {
            myImageView = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.grid_view_poster, container, false);
        } else {
            myImageView = (ImageView) recycled;
        }

        MovieDetails movie = getItem(position);

	   /* Glide.with(myFragment)
	        .load(url)
	        .centerCrop()
	        .placeholder(R.drawable.loading_spinner)
	        .crossFade()
	        .into(myImageView);*/




        Picasso.with(getContext())
                .load(movie.getPosterPath())
                .resize(100, 100) //testing new things
                .centerCrop()
                .into(myImageView);


        return myImageView;
    }
}