package com.example.amrarafa.movies;

/**
 * Created by amr arafa on 2/17/2016.
 */
public class MovieDetails {



    private String title;
    private String posterPath;
    private String releaseDate;
    private String voteAverage;
    private String overview;
    private int id;

    public MovieDetails(String posterPath,
                        String title,
                        String releaseDate,
                        String voteAverage,
                        String overview,
                        int id ) {
        this.title = title;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.id=id;
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





}
