package app.movie.tutorial.com.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import app.movie.tutorial.com.activity.MainActivity;
import app.movie.tutorial.com.model.MovieAPIModel;
import retrofit2.http.Query;

import static app.movie.tutorial.com.data.FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID;
import static app.movie.tutorial.com.data.FavoritesContract.FavoritesEntry.TABLE_NAME;

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

            db.insert(TABLE_NAME, null, cv);
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

    public static boolean checkMovieExist(SQLiteDatabase mdb, int movieId){
        boolean result = true;
        Cursor cursor = mdb.query(
                TABLE_NAME,
                new String[] {COLUMN_MOVIE_ID},
                new String (COLUMN_MOVIE_ID + "=?"),
                new String[]{String.valueOf(movieId)},
                null,
                null,
                null,
                null);

        if (cursor.getCount() <= 0){
            result = false;
        }
        cursor.close();
        return result;
    }

    public static void deleteMovie(SQLiteDatabase mDb, MovieAPIModel favorite) {
        mDb.delete(
                TABLE_NAME,
                COLUMN_MOVIE_ID + "=" + favorite.getId(),
                null);
    }
}
