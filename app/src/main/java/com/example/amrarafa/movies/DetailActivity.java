package com.example.amrarafa.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {

    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
     

        tv=(TextView)findViewById(R.id.tvForTesting);
        Bundle data = getIntent().getExtras();
        MovieDetails movie =(MovieDetails)data.getParcelable("movieDetail");
        tv.setText(movie.getReleaseDate());




    }

}
