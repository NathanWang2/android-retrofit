package app.movie.tutorial.com.data;

import android.database.Cursor;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import app.movie.tutorial.com.model.MovieAPIModel;

public class CursorToMovieModel {

    Cursor cursor;

    public CursorToMovieModel(Cursor cursor) {
        this.cursor = cursor;
    }

    public ArrayList<MovieAPIModel> getListOfMovies(){

        ArrayList<MovieAPIModel> result = new ArrayList<MovieAPIModel>();
        for (int i = 0; i < cursor.getCount(); i++){
            result.add(loop(cursor, i));
        }
        return result;
    }

    private MovieAPIModel loop(Cursor cursor, int i){
        MovieAPIModel temp = new MovieAPIModel();

        cursor.moveToPosition(i);
        temp.setId(cursor.getInt(cursor.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID)));
        temp.setOriginalTitle(cursor.getString(cursor.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_MOVIE_TITLE)));
        temp.setOverview(cursor.getString(cursor.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_MOVIE_DETAILS)));
        temp.setPosterPath(cursor.getString(cursor.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_MOVIE_POSTER_URL)));
        temp.setVoteAverage(cursor.getDouble(cursor.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_MOVIE_RATING)));
        temp.setReleaseDate(cursor.getString(cursor.getColumnIndex(FavoritesContract.FavoritesEntry.COLUMN_MOVIE_RELEASE_DATE)));
        return temp;
    }
}
