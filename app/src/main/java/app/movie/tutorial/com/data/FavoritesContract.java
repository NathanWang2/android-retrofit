package app.movie.tutorial.com.data;

import android.provider.BaseColumns;

public class FavoritesContract {

    public static final class FavoritesEntry implements BaseColumns{

        public static final String TABLE_NAME = "favorites";
        public static final String COLUMN_MOVIE_ID = "MovieId";
        public static final String COLUMN_MOVIE_TITLE = "MovieTitle";
        public static final String COLUMN_MOVIE_POSTER_URL = "MoviePoster";
        public static final String COLUMN_MOVIE_DETAILS = "MovieDetails";
        public static final String COLUMN_MOVIE_RATING = "MovieRating";
        public static final String COLUMN_MOVIE_RELEASE_DATE = "MovieReleaseDate";

    }

}
