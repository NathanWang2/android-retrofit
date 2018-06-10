package app.movie.tutorial.com.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import app.movie.tutorial.com.R;
import app.movie.tutorial.com.adapter.MoviesAdapter;
import app.movie.tutorial.com.adapter.RecyclerViewAdapter;
import app.movie.tutorial.com.layoutManager.AutoFitGridLayoutManager;
import app.movie.tutorial.com.model.MovieAPIModel;
import app.movie.tutorial.com.model.MovieModel;
import app.movie.tutorial.com.rest.MovieApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemListener{

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;
    private RecyclerView recyclerView;

    // insert your themoviedb.org API KEY here
    private final static String API_KEY = "e5d9e7a3d1a18c5caa632a613d622aae";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        connectAndGetApiData();

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
                Toast.makeText(this, "Dropdown 1", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_dropdown_2:
                Toast.makeText(this, "Dropdown 2", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_dropdown_3:
                Toast.makeText(this, "Dropdown 3", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // This method create an instance of Retrofit
    // set the base url
    public void connectAndGetApiData(){

        final AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(MainActivity.this, 500);
        recyclerView.setLayoutManager(layoutManager);

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        MovieApiService movieApiService = retrofit.create(MovieApiService.class);

//      Change the method movieApiService.WhateverMovieAPIService(key)
        Call<MovieModel> call = movieApiService.getPoplarMovies(API_KEY);
        call.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                List<MovieAPIModel> movies = response.body().getResults();

//                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));

                recyclerView.setAdapter(new RecyclerViewAdapter(getApplicationContext(), movies, MainActivity.this));
                recyclerView.setLayoutManager(layoutManager);

                Log.d(TAG, "Number of movies received: " + movies.size());

            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }
        });
    }

    @Override
    public void onItemClick(MovieAPIModel item) {
        Intent intent = new Intent(MainActivity.this, MovieDetailActivity.class);
        intent.putExtra("MoviePoster", item.getPosterPath());
        intent.putExtra("MovieTitle", item.getTitle());
        intent.putExtra("MovieDetails", item.getOverview());
        intent.putExtra("MovieRating", String.valueOf(item.getVoteAverage()));
        intent.putExtra("MovieReleaseDate", item.getReleaseDate());
        startActivity(intent);
    }
}
