package app.movie.tutorial.com.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import app.movie.tutorial.com.Key;
import app.movie.tutorial.com.R;

import app.movie.tutorial.com.adapter.RecyclerViewAdapter;
import app.movie.tutorial.com.data.CursorToMovieModel;
import app.movie.tutorial.com.data.FavoritesContract;
import app.movie.tutorial.com.data.FavoritesDbHelper;
import app.movie.tutorial.com.layoutManager.AutoFitGridLayoutManager;
import app.movie.tutorial.com.model.MovieAPIModel;
import app.movie.tutorial.com.model.MovieModel;
import app.movie.tutorial.com.rest.MovieApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * The repository is forked. You can also go to https://github.com/Ginowine/android-retrofit
 * To find where the original project came from.
 *
 * Created by Gino Osahon on 14/03/2017.
 * Edited by Nathan Wang 6/4/2018.
 */



public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    public final static String API_KEY = Key.API_KEY;
    private static Retrofit retrofit = null;
    private RecyclerView recyclerView;
    public int CurrentPage = 1;
    public int SortMethod = 1;
    public ArrayList<MovieAPIModel> movies;
    public final AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(MainActivity.this, 300);
    public SQLiteDatabase mDb;
    public static FavoritesDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        connectAndGetApiData(SortMethod, CurrentPage);



//        Load more
        final int[] pastVisibleItems = new int[1];
        final int[] visibleItemCount = new int[1];
        final int[] totalItemCount = new int[1];

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(dy > 0) //check for scroll down
                {
                    visibleItemCount[0] = layoutManager.getChildCount();
                    totalItemCount[0] = layoutManager.getItemCount();
                    pastVisibleItems[0] = layoutManager.findFirstVisibleItemPosition();

                    if ( (visibleItemCount[0] + pastVisibleItems[0]) >= totalItemCount[0])
                    {
//                        Refreshes at the end of the list
//                        Log.v("...", "Last Item Wow !");
//                        //Do pagination.. i.e. fetch new data

                        connectAndGetApiData(SortMethod, ++CurrentPage);
                    }

                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_dropdown_1:
                SortMethod = 1;
                connectAndGetApiData(SortMethod, 1);
                return true;

            case R.id.action_dropdown_2:
                SortMethod = 2;
                connectAndGetApiData(SortMethod, 1);
                return true;

            case R.id.action_dropdown_3:
                SortMethod = 3;
                dbHelper = new FavoritesDbHelper(this);
                mDb = dbHelper.getReadableDatabase();

                Log.d("FAVORITES", "We are in the favorites");
                Cursor test = getAllMovies();
                if (test != null) {
                    CursorToMovieModel listOfMovies = new CursorToMovieModel(test);
                    addScreenItem(listOfMovies.getListOfMovies());
                    Toast.makeText(this, "THIS TABLE HAS CONTENT", Toast.LENGTH_SHORT).show();
                }
                test.close();
                mDb.close();
                dbHelper.close();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // This method create an instance of Retrofit
    // set the base url
    public void connectAndGetApiData(int sort, final int page){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        MovieApiService movieApiService = retrofit.create(MovieApiService.class);

//        Chooses the sorting method
        Call<MovieModel> call;
        int SortMethod = sort;
        switch (SortMethod){
            case 1:
                call = movieApiService.getPoplarMovies(API_KEY, page);
                break;
            case 2:
                call = movieApiService.getTopRatedMovies(API_KEY, page);
                break;
            default:
                call = movieApiService.getPoplarMovies(API_KEY, page);

        }
        call.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if (page == 1){
                    movies = response.body().getResults();

                    recyclerView.setAdapter(new RecyclerViewAdapter(getApplicationContext(), movies, MainActivity.this));
                    recyclerView.setLayoutManager(layoutManager);
                } else {
                    List<MovieAPIModel> temp = response.body().getResults();
                    movies.addAll(temp);
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
                Log.d(TAG, "Number of movies received: " + movies.size());
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }

    public void addScreenItem(ArrayList<MovieAPIModel> listMovies){
        recyclerView.setAdapter(new RecyclerViewAdapter(getApplicationContext(), listMovies, MainActivity.this));
        recyclerView.setLayoutManager(layoutManager);
    }

    private Cursor getAllMovies(){
//        return mDb.query(
//                FavoritesContract.FavoritesEntry.TABLE_NAME,
//                null,
//                null,
//                null,
//                null,
//                null,
//                null
//        );
        return null;
    }


    @Override
    public void onItemClick(MovieAPIModel item) {
        Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
        intent.putExtra("MoviePoster", item.getPosterPath());
        intent.putExtra("MovieTitle", item.getTitle());
        intent.putExtra("MovieDetails", item.getOverview());
        intent.putExtra("MovieRating", String.valueOf(item.getVoteAverage()));
        intent.putExtra("MovieReleaseDate", item.getReleaseDate());
        intent.putExtra("MovieId", item.getId());
        startActivity(intent);
    }
}
