package app.movie.tutorial.com.data;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import app.movie.tutorial.com.activity.MainActivity;
import app.movie.tutorial.com.model.MovieAPIModel;

public class DatabaseUtils {

    public static void insertMovie(SQLiteDatabase db, MovieAPIModel movie){

        if(db == null){
            return;
        }

        ContentValues cv = new ContentValues();
        cv.put(FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID, movie.getId());
        cv.put(FavoritesContract.FavoritesEntry.COLUMN_MOVIE_DETAILS, movie.getOverview());
        cv.put(FavoritesContract.FavoritesEntry.COLUMN_MOVIE_POSTER_URL, movie.getPosterPath());
        cv.put(FavoritesContract.FavoritesEntry.COLUMN_MOVIE_RATING, movie.getVoteAverage());
        cv.put(FavoritesContract.FavoritesEntry.COLUMN_MOVIE_RELEASE_DATE, movie.getReleaseDate());
        cv.put(FavoritesContract.FavoritesEntry.COLUMN_MOVIE_TITLE, movie.getTitle());

        try
        {
            db.beginTransaction();

            db.insert(FavoritesContract.FavoritesEntry.TABLE_NAME, null, cv);
            db.setTransactionSuccessful();
        }
        catch (SQLException e) {
            //too bad :(
        }
        finally
        {
            db.endTransaction();
        }
    }
}
