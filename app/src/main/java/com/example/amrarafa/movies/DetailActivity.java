package com.example.amrarafa.movies;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.amrarafa.movies.data.MovieContract;

public class DetailActivity extends AppCompatActivity {

    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent=this.getIntent();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        tv=(TextView)findViewById(R.id.tvForTesting);


        if (intent!=null){

            Cursor cursor=this.getContentResolver().query(intent.getData(),null,null,null,null);

           // int data =cursor.getInt(cursor.getColumnIndex(MovieContract.MostPopular.COLUMN_POSTER_PATH));
          //  tv.setText(data);

        }


     //   String data = getIntent().getDataString();
       // tv.setText(data);




    }


    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {




            View rootView = inflater.inflate(R.layout.detail_fragment, container, false);



            return rootView;
        }
    }

}
