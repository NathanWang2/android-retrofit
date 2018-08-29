package app.movie.tutorial.com.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavoritesDbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "favorites.db";
    private static final int DATABASE_VERSION = 1;

    public FavoritesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_FAVORITES_TABLE = "CREATE TABLE " + FavoritesContract.FavoritesEntry.TABLE_NAME + " (" +
                FavoritesContract.FavoritesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                FavoritesContract.FavoritesEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
                FavoritesContract.FavoritesEntry.COLUMN_MOVIE_POSTER_URL + " TEXT NOT NULL, " +
                FavoritesContract.FavoritesEntry.COLUMN_MOVIE_DETAILS + " TEXT NOT NULL, " +
                FavoritesContract.FavoritesEntry.COLUMN_MOVIE_RATING + " INTEGER NOT NULL, " +
                FavoritesContract.FavoritesEntry.COLUMN_MOVIE_RELEASE_DATE + " TEXT NOT NULL " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FAVORITES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavoritesContract.FavoritesEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
}
