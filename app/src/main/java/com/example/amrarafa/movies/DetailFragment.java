package com.example.amrarafa.movies;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.amrarafa.movies.data.MovieContract;
import com.example.amrarafa.movies.data.MoviesDbHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;

/**
 * Created by amr arafa on 4/20/2016.
 */

public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    private static final int DETAIL_LOADER = 1;
    private static final int FAVOURITE_LOADER=2;
    public String mMovieTrailerId1 =null;
    public String mMovieTrailerId2 =null;
    public static Cursor mCursor;
    public static int isItFavourite=1;
    public static TextView mOverViewTextView;
    public static ImageView mImageView;
    public static TextView mTitleTextView;
    public static TextView mVoteTextView;
    public static TextView mDateTextView;
    public static Button mFavouriteButton;




    public DetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {

        Intent intent =getActivity().getIntent();
        Uri uri=intent.getData();
        Long id= MovieContract.MostPopular.getIdUri(uri);
        fetchUrl(String.valueOf(id));

        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.detail_fragment, container, false);

        mOverViewTextView = (TextView)rootView.findViewById(R.id.testText);
        mImageView =(ImageView)rootView.findViewById(R.id.imageViewDetailFragment);
        mTitleTextView =(TextView)rootView.findViewById(R.id.titleID);
        mDateTextView=(TextView)rootView.findViewById(R.id.dateTextView);
        mVoteTextView=(TextView)rootView.findViewById(R.id.voteTextView);
        mFavouriteButton=(Button)rootView.findViewById(R.id.favouriteButton);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.my_toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(activity.getResources().getColor(R.color.colorCustom));

        ImageButton btn1= (ImageButton)rootView.findViewById(R.id.btn1);
        ImageButton btn2= (ImageButton)rootView.findViewById(R.id.btn2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new  Intent(Intent.ACTION_VIEW);

                intent.setPackage("com.google.android.youtube");
                intent.setData(Uri.parse("https://www.youtube.com/watch?v="+mMovieTrailerId1));

                startActivity(intent);

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);

                intent.setPackage("com.google.android.youtube");
                intent.setData(Uri.parse("https://www.youtube.com/watch?v="+mMovieTrailerId2));

                startActivity(intent);

            }
        });

        mFavouriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isItFavourite==1){
                    insertIntofavourite();
                    mFavouriteButton.setText("REMOVE FROM FAVOURITE");
                    isItFavourite=2;
                }else if(isItFavourite==2){

                    mFavouriteButton.setText("MARK AS FAVOURITE");
                    isItFavourite=1;
                    deleteFromFavourite();
                }

            }
        });

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        getLoaderManager().initLoader(FAVOURITE_LOADER, null, this);

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Intent intent = getActivity().getIntent();
        Uri uri=intent.getData();
        Long movieId= MovieContract.MostPopular.getIdUri(uri);
        Uri favouriteUri=MovieContract.Favourite.buildIdUri(movieId);



        if (intent == null) {
            return null;
        }

        if (id==1){
            return new CursorLoader(
                    getActivity(),
                    uri,
                    null,
                    null,
                    null,
                    null
            );

        }

        return new CursorLoader(
                getActivity(),
                favouriteUri,
                null,
                null,
                null,
                null
        );
        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.

    }



    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {


        if (loader.getId()==1) {

            mCursor=data;

            if (!data.moveToFirst()) {
                return;
            }

            String title= data.getString(data.getColumnIndex(MovieContract.MostPopular
                    .COLUMN_TITLE));

            mOverViewTextView.setText(data.getString(data.getColumnIndex(MovieContract.MostPopular.COLUMN_OVERVIEW)));
            mDateTextView.setText(data.getString(data.getColumnIndex(MovieContract.MostPopular.COLUMN_RELEASE_DATE)));
            mTitleTextView.setText(title);
            mVoteTextView.setText(String.valueOf(data.getDouble(data.getColumnIndex(MovieContract.MostPopular.COLUMN_VOTE_AVERAGE))));

            String poster_path=data.getString(data.getColumnIndex(MovieContract.
                    MostPopular.COLUMN_POSTER_PATH));

            Picasso.with(getActivity())
                    .load(poster_path)
                    .fit()
                    .into(mImageView);





        }



       else if(loader.getId()==2){
            if (!data.moveToFirst()){

                isItFavourite=1;//not in favourite
                mFavouriteButton.setText("MARK AS FAVOURITE");

//                Intent intent=getActivity().getIntent();
//                Uri uri=intent.getData();
//                Long id= MovieContract.MostPopular.getIdUri(uri);
//                Uri favouriteUri=MovieContract.Favourite.buildIdUri(id);
//                mFavouriteButton.setText("MARK AS FAVOURITE");
//                ContentValues movieValues = new ContentValues();
//                MoviesDbHelper dbHelper = new MoviesDbHelper(getActivity());
//                SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//
//                movieValues.put(MovieContract.MostPopular.COLUMN_POSTER_PATH, "http://image.tmdb.org/t/p/w185/" +
//                        mCursor.getString(
//                        mCursor.getColumnIndex(MovieContract.MostPopular.COLUMN_POSTER_PATH)));
//
//                movieValues.put(MovieContract.MostPopular.COLUMN_ID,mCursor.getInt(mCursor.getColumnIndex(
//                        MovieContract.MostPopular.COLUMN_ID)));
//
//                movieValues.put(MovieContract.MostPopular.COLUMN_OVERVIEW, mCursor.getString(
//                        mCursor.getColumnIndex(MovieContract.MostPopular.COLUMN_OVERVIEW)));
//
//                movieValues.put(MovieContract.MostPopular.COLUMN_RELEASE_DATE, mCursor.getString(
//                        mCursor.getColumnIndex(MovieContract.MostPopular.COLUMN_RELEASE_DATE)));
//
//                movieValues.put(MovieContract.MostPopular.COLUMN_TITLE, mCursor.getString(
//                        mCursor.getColumnIndex(MovieContract.MostPopular.COLUMN_TITLE)));
//
//                movieValues.put(MovieContract.MostPopular.COLUMN_VOTE_AVERAGE, mCursor.getDouble(
//                        mCursor.getColumnIndex(MovieContract.MostPopular.COLUMN_VOTE_AVERAGE)));
//
//
//                Uri testUri = getActivity().getContentResolver().insert(favouriteUri,movieValues);
//                Toast.makeText(getActivity(),""+testUri.toString(),Toast.LENGTH_LONG).show();



            }else {
                isItFavourite=2;//in favoyrite table
                mFavouriteButton.setText("REMOVE FROM FAVOURITE");
            }

        }



    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    void insertIntofavourite(){

        Intent intent=getActivity().getIntent();
        Uri uri=intent.getData();
        Long id= MovieContract.MostPopular.getIdUri(uri);
        Uri favouriteUri=MovieContract.Favourite.buildIdUri(id);
        MoviesDbHelper dbHelper = new MoviesDbHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        ContentValues movieValues = new ContentValues();

        movieValues.put(MovieContract.MostPopular.COLUMN_POSTER_PATH, "http://image.tmdb.org/t/p/w185/" +
                mCursor.getString(
                        mCursor.getColumnIndex(MovieContract.MostPopular.COLUMN_POSTER_PATH)));

        movieValues.put(MovieContract.MostPopular.COLUMN_ID,mCursor.getInt(mCursor.getColumnIndex(
                MovieContract.MostPopular.COLUMN_ID)));

        movieValues.put(MovieContract.MostPopular.COLUMN_OVERVIEW, mCursor.getString(
                mCursor.getColumnIndex(MovieContract.MostPopular.COLUMN_OVERVIEW)));

        movieValues.put(MovieContract.MostPopular.COLUMN_RELEASE_DATE, mCursor.getString(
                mCursor.getColumnIndex(MovieContract.MostPopular.COLUMN_RELEASE_DATE)));

        movieValues.put(MovieContract.MostPopular.COLUMN_TITLE, mCursor.getString(
                mCursor.getColumnIndex(MovieContract.MostPopular.COLUMN_TITLE)));

        movieValues.put(MovieContract.MostPopular.COLUMN_VOTE_AVERAGE, mCursor.getDouble(
                mCursor.getColumnIndex(MovieContract.MostPopular.COLUMN_VOTE_AVERAGE)));


        Long favouriteId =db.insert(MovieContract.Favourite.TABLE_NAME,null,movieValues);
//        Uri testUri = getActivity().getContentResolver().insert(MovieContract.Favourite.CONTENT_URI,movieValues);
        db.close();
        Toast.makeText(getActivity(),""+favouriteId.toString(),Toast.LENGTH_LONG).show();
    }

    void deleteFromFavourite(){

        Intent intent=getActivity().getIntent();
        Uri uri = intent.getData();
        Long id= MovieContract.MostPopular.getIdUri(uri);
        MoviesDbHelper dbHelper = new MoviesDbHelper(getActivity());
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        String[] selectionArgs;
        selectionArgs = new String[]{Long.toString(id)};

        int favouriteId =db.delete(MovieContract.Favourite.TABLE_NAME,MovieContract.Favourite.COLUMN_ID +"= ?",selectionArgs);
//        Uri testUri = getActivity().getContentResolver().insert(MovieContract.Favourite.CONTENT_URI,movieValues);
        db.close();
        Toast.makeText(getActivity(),"Deleted isa",Toast.LENGTH_LONG).show();
    }


    void fetchUrl(final String trailerId){

        Log.d("Lion King", trailerId);



        String url= " http://api.themoviedb.org/3/movie/"+trailerId+"/videos?api_key=19dfd5ebe589153dc9d6788c7c9f347b";



        final JSONObject responseObject;

        RequestQueue requestQueue;

        requestQueue= Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        ParseTrailer parseTrailer=new ParseTrailer();
                        parseTrailer.execute(response);
//                        ParsingTask ps =new ParsingTask(getActivity(),trailerId);
//                        ps.execute(response);


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

    public class ParseTrailer extends AsyncTask<JSONObject,Void,Void>
    {
        public ParseTrailer(){

        }

        @Override
        protected Void doInBackground(JSONObject... params) {

            try {
                JSONArray jsonArrayResult=params[0].getJSONArray("results");
                JSONObject result1= jsonArrayResult.getJSONObject(2);
                mMovieTrailerId1 =result1.getString("key");
                Log.d("Trailer Key1", mMovieTrailerId1);
                result1=jsonArrayResult.getJSONObject(1);
                mMovieTrailerId2=result1.getString("key");
                Log.d("Trailer Key2",mMovieTrailerId2);
            }
            catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }
    }

}
