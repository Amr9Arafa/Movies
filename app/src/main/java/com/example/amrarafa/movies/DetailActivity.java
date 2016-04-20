package com.example.amrarafa.movies;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.amrarafa.movies.data.MovieContract;

public class DetailActivity extends ActionBarActivity {

    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent=this.getIntent();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }

        tv=(TextView)findViewById(R.id.tvForTesting);

//
//        if (intent!=null){
//
//            Cursor cursor=this.getContentResolver().query(intent.getData(),null,null,null,null);
//
//            cursor.moveToNext();
//            String data =cursor.getString(cursor.getColumnIndex(MovieContract.MostPopular.COLUMN_POSTER_PATH));
//            tv.setText(data);
//
//        }


     //   String data = getIntent().getDataString();
       // tv.setText(data);




    }



}
