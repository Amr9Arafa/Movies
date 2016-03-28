package com.example.amrarafa.movies;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.amrarafa.movies.data.MovieContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MovieFragment extends Fragment {

    MovieCursorAdapter mMovieAdapter;


    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchUrl("popularity");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);

        Uri uri = MovieContract.MostPopular.CONTENT_URI;

        Cursor cursor= getActivity().getContentResolver().query(uri,null,null,null,null);

        mMovieAdapter = new MovieCursorAdapter(getActivity(),cursor,0);
        GridView gridView=(GridView) rootView.findViewById(R.id.gridView);
        gridView.setAdapter(mMovieAdapter);

//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//
//                MovieDetails movie =(MovieDetails)parent.getItemAtPosition(position);
//                Intent intent=new Intent(getActivity(),DetailActivity.class);
//                intent.putExtra("movieDetail",new MovieDetails(movie.getPosterPath(),movie.getTitle(),movie.getReleaseDate(),movie.getVoteAverage(),
//                        movie.getOverview(),movie.getId()));
//
//                startActivity(intent);
//            }
//        });

        return rootView;
    }

    void fetchUrl(String movieListType){

        RequestQueue requestQueue;

        String url="https://api.themoviedb.org/3/discover/movie?api_key=19dfd5ebe589153dc9d6788c7c9f347b&sort_by="
                +movieListType+".desc";

        requestQueue= Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        ParsingTask ps =new ParsingTask(getActivity());
                        ps.execute();


                        Log.w("Testing",response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.v("myApp","yallaa"+error.toString());
                    }
                });

        Volley.newRequestQueue(getActivity()).add(jsObjRequest);

    }



}
