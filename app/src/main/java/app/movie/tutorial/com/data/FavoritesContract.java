package app.movie.tutorial.com.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class FavoritesContract {
    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "app.movie.tutorial.com";
    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    // Define the possible paths for accessing data in this contract
    // This is the path for the "tasks" directory
    public static final String PATH_FAVORITES = "favorites";

    public static final class FavoritesEntry implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITES).build();

        public static final String TABLE_NAME = "favorites";
        public static final String COLUMN_MOVIE_ID = "MovieId";
        public static final String COLUMN_MOVIE_TITLE = "MovieTitle";
        public static final String COLUMN_MOVIE_POSTER_URL = "MoviePoster";
        public static final String COLUMN_MOVIE_DETAILS = "MovieDetails";
        public static final String COLUMN_MOVIE_RATING = "MovieRating";
        public static final String COLUMN_MOVIE_RELEASE_DATE = "MovieReleaseDate";

    }
}
