package com.example.amrarafa.movies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by amr arafa on 2/17/2016.
 */
public class MovieDetails implements Parcelable {



    private String title;
    private String posterPath;
    private String releaseDate;
    private String voteAverage;
    private String overview;
    private String id;

    public MovieDetails(String posterPath,
                        String title,
                        String releaseDate,
                        String voteAverage,
                        String overview,
                        String id ) {
        this.posterPath = posterPath;
        this.title = title;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.id=id;
    }


    public MovieDetails(Parcel in){
        String[] data= new String[6];
        in.readStringArray(data);
        this.posterPath=data[0];
        this.title=data[1];
        this.releaseDate=data[2];
        this.voteAverage=data[3];
        this.overview=data[4];
        this.id=data[5];

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                this.posterPath,
                this.title,
                this.releaseDate,
                this.voteAverage,
                this.overview,
                this.id});
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public String getId() {
        return id;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public MovieDetails createFromParcel(Parcel in) {
            return new MovieDetails(in);
        }

        public MovieDetails[] newArray(int size) {
            return new MovieDetails[size];
        }
    };


}
