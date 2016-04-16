package com.example.amrarafa.movies;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;

import com.example.amrarafa.movies.data.MovieContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by amr arafa on 3/28/2016.
 */
public class ParsingTask extends AsyncTask<JSONObject, Void, Void> {


    private final Context mContext;

    public ParsingTask(Context context){
        mContext=context;
    }

    @Override
    protected Void doInBackground(JSONObject... params) {

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

            Vector<ContentValues> cVVector = new Vector<ContentValues>(jsonResult.length());


            for(int i=0;i<jsonResult.length();i++){

                JSONObject jj= jsonResult.getJSONObject(i);
                poster_path=jj.getString(poster);
                id=jj.getInt("id");


                ContentValues movieValues = new ContentValues();

                movieValues.put(MovieContract.MostPopular.COLUMN_POSTER_PATH,"http://image.tmdb.org/t/p/w185/"+poster_path);
                movieValues.put(MovieContract.MostPopular.COLUMN_ID,id);
                movieValues.put(MovieContract.MostPopular.COLUMN_OVERVIEW,jj.getString(overview));
                movieValues.put(MovieContract.MostPopular.COLUMN_RELEASE_DATE,jj.getString(releaseDate));
                movieValues.put(MovieContract.MostPopular.COLUMN_TITLE,jj.getString(title));
                movieValues.put(MovieContract.MostPopular.COLUMN_VOTE_AVERAGE, voteAverage);


                cVVector.add(movieValues);


                int inserted = 0;
                // add to database
                if ( cVVector.size() > 0 ) {
                    ContentValues[] cvArray = new ContentValues[cVVector.size()];
                    cVVector.toArray(cvArray);
                 inserted = mContext.getContentResolver().bulkInsert(MovieContract.MostPopular.CONTENT_URI, cvArray);

                }


                // Log.w("testing id", id + "\n");
            }





        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}