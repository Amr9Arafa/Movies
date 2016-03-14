package com.example.amrarafa.movies.data;

import android.provider.BaseColumns;

/**
 * Created by amr arafa on 3/12/2016.
 */
public class MovieContract {

    public static final class MostPopular implements BaseColumns {

        public static final String TABLE_NAME = "most popular";

        public static final String COLUMN_TITLE = "title";

        public static final String COLUMN_POSTER_PATH = "poster path";

        public static final String COLUMN_RELEASE_DATE = "release date";

        public static final String COLUMN_VOTE_AVERAGE = "vote average";

        public static final String COLUMN_ID = "id";

        public static final String COLUMN_OVERVIEW = "overview";



    }

    public static final class HighestRated implements BaseColumns {


        public static final String TABLE_NAME = "highest rated";

        public static final String COLUMN_TITLE = "title";

        public static final String COLUMN_POSTER_PATH = "poster path";

        public static final String COLUMN_RELEASE_DATE = "release date";

        public static final String COLUMN_VOTE_AVERAGE = "vote average";

        public static final String COLUMN_ID = "id";

        public static final String COLUMN_OVERVIEW = "overview";

    }

    public static final class Favourite implements BaseColumns {


        public static final String TABLE_NAME = "favourite";

        public static final String COLUMN_TITLE = "title";

        public static final String COLUMN_POSTER_PATH = "poster path";

        public static final String COLUMN_RELEASE_DATE = "release date";

        public static final String COLUMN_VOTE_AVERAGE = "vote average";

        public static final String COLUMN_ID = "id";

        public static final String COLUMN_OVERVIEW = "overview";

    }
}
