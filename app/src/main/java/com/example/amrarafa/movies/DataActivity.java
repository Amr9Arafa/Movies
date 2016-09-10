package com.example.amrarafa.movies;

import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.amrarafa.movies.data.MovieContract;

import org.json.JSONObject;

public abstract class  DataActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    private LoaderManager.LoaderCallbacks<Cursor> mCallbacks;
    private static final int MOVIE_LOADER = 1;
    Cursor mCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCallbacks=this;
        getLoaderManager().initLoader(MOVIE_LOADER, null, mCallbacks);


    }
    protected void fetchUrl(){

        String type=getParams();
        Fetch fetch=new Fetch(type, this, new Fetch.OnFinishedFetching() {
            @Override
            public void onFinishedFeching(Context context, String movieListType, JSONObject response) {
                ParsingTask ps =new ParsingTask(DataActivity.this,movieListType);
                ps.execute(response);
            }
        });
        getLoaderManager().restartLoader(MOVIE_LOADER, null,this);



    }

    public abstract String getParams();

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {



        Uri uri = MovieContract.MostPopular.CONTENT_URI;
        if (getParams().equals("Highest Rated")) {
            uri = MovieContract.HighestRated.CONTENT_URI;

        }
        else if(getParams().equals("Favourite")){
            uri=MovieContract.Favourite.CONTENT_URI;
        }
        return new CursorLoader(this,
                uri,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {

        mCursor=cursor;
        if (!(mCursor.moveToFirst()) || mCursor.getCount() ==0){
            //cursor is empty
            ((OnDataReady) this).onError();

        }
        else {


            ((OnDataReady) this).onDataFetched(mCursor);
        }
    }

    @Override
    public void onLoaderReset(
            Loader<Cursor> cursorLoader) {
        mCursor=null;
        ((OnDataReady) this).onDataFetched(mCursor);

    }


    public interface OnDataReady {

        void onDataFetched(Cursor cursor);
        void onError();
    }

}
