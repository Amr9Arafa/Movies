package com.example.amrarafa.movies;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MovieFragment extends Fragment {

    MovieAdapter mMovieAdapter;


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

        mMovieAdapter = new MovieAdapter(getActivity(), new ArrayList<MovieDetails>());
        GridView gridView=(GridView) rootView.findViewById(R.id.gridView);
        gridView.setAdapter(mMovieAdapter);

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


                        ParsingTask ps =new ParsingTask();
                        ps.execute(response);

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

    public class ParsingTask extends AsyncTask<JSONObject, Void, ArrayList<MovieDetails>> {

        @Override
        protected ArrayList<MovieDetails> doInBackground(JSONObject... params) {


            ArrayList<MovieDetails> moviesDetails= new ArrayList<MovieDetails>();

            //	String[] movieLink;

            final String poster="poster_path";
            final String title="original_title";
            final String releaseDate="release_date";
            final String voteAverage="vote_average";
            final String overview="overview";
            int id;



            String poster_path="";

            try
            {
                JSONArray jsonResult = params[0].getJSONArray("results");

                for(int i=0;i<jsonResult.length();i++){

                    JSONObject jj= jsonResult.getJSONObject(i);
                    poster_path=jj.getString(poster);
                    // poster_path=+poster_path;
                    id=jj.getInt("id");
                    moviesDetails.add(new MovieDetails("http://image.tmdb.org/t/p/w185/"+poster_path,jj.getString(releaseDate),
                            jj.getString(releaseDate),jj.getString(releaseDate),jj.getString(releaseDate),jj.getInt("id")));
                   // Log.w("testing id", id + "\n");
                }


                // movieLink[0] = jj.getString(poster);

                //updateUI(movieLink);

                //Toast.makeText(getActivity(), poster, Toast.LENGTH_LONG).show();

                //  Log.w("Testing",movieLink[0]);


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


            // TODO Auto-generated method stub
            return moviesDetails;
        }


        @Override
        protected void onPostExecute(ArrayList<MovieDetails> movieDetails) {

            if(movieDetails!=null){
                mMovieAdapter.clear();
                for (MovieDetails movieItem : movieDetails){
                    mMovieAdapter.add(movieItem);
                }
            }

        }



    }

}
