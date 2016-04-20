package com.example.amrarafa.movies;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.amrarafa.movies.data.MovieContract;

/**
 * Created by amr arafa on 4/20/2016.
 */

public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    //        TextView textView;
    private static final int DETAIL_LOADER = 1;

    public DetailFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.detail_fragment, container, false);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.my_toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(activity.getResources().getColor(R.color.colorCustom));
        Intent intent=getActivity().getIntent();

//            textView=(TextView)rootView.findViewById(R.id.testText);
//
//            if (intent!=null){
//
//                Cursor cursor=getActivity().getContentResolver().query(intent.getData(),null,null,null,null);
//
//                cursor.moveToNext();
//                String data =cursor.getString(cursor.getColumnIndex(MovieContract.MostPopular.COLUMN_POSTER_PATH));
//                textView.setText(data);
//
//            }




        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Intent intent = getActivity().getIntent();
        if (intent == null) {
            return null;
        }

        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        return new CursorLoader(
                getActivity(),
                intent.getData(),
                null,
                null,
                null,
                null
        );
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (!data.moveToFirst()) { return; }

        TextView detailTextView = (TextView)getView().findViewById(R.id.testText);
        detailTextView.setText(data.getString(data.getColumnIndex(MovieContract.MostPopular.COLUMN_OVERVIEW)));


    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

}
