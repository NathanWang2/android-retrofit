package app.movie.tutorial.com.activity;

import android.content.Intent;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import app.movie.tutorial.com.R;
import app.movie.tutorial.com.adapter.ReviewViewAdapter;
import app.movie.tutorial.com.adapter.TrailerViewAdapter;
import app.movie.tutorial.com.data.ContentProvider;
import app.movie.tutorial.com.data.DatabaseUtils;
import app.movie.tutorial.com.data.FavoritesContract;
import app.movie.tutorial.com.data.FavoritesDbHelper;
import app.movie.tutorial.com.model.ListOfTrailers;
import app.movie.tutorial.com.model.MovieAPIModel;
import app.movie.tutorial.com.model.ReviewsModel;
import app.movie.tutorial.com.model.TrailerModel;
import app.movie.tutorial.com.rest.MovieApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static app.movie.tutorial.com.activity.MainActivity.API_KEY;
import static app.movie.tutorial.com.activity.MainActivity.BASE_URL;


public class MovieDetailActivity extends AppCompatActivity {

    private static Retrofit retrofit = null;
    private List<ListOfTrailers> listOfTrailers;
    public RecyclerView mTrailerView;
    private ArrayList<ReviewsModel.ReviewList> reviewsModel;
    public RecyclerView mReviewView;
    public Button favoriteButton;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_info);

        ImageView mPoster = (ImageView) findViewById(R.id.PosterTV);
        TextView mTitle = (TextView) findViewById(R.id.MovieTitleTV);
        TextView mMovieDesc = (TextView) findViewById(R.id.MovieDescTV);
        TextView mMovieRating = (TextView) findViewById(R.id.rating);
        TextView mMovieReleaseDate = (TextView) findViewById(R.id.MovieReleaseDateTV);
        mTrailerView = (RecyclerView) findViewById(R.id.trailerRecyclerView);
        mReviewView = (RecyclerView) findViewById(R.id.reviewRecyclerView);
        favoriteButton = (Button) findViewById(R.id.favoriteButton);
        final MovieAPIModel favorite = new MovieAPIModel();

        FavoritesDbHelper dbHelper = FavoritesDbHelper.getInstance(getApplicationContext());
        mDb = dbHelper.getWritableDatabase();


        Intent intent = getIntent();

        if (intent.hasExtra("MoviePoster")){
            String posterPath = intent.getStringExtra("MoviePoster");

            favorite.setPosterPath(posterPath);

            String IMAGE_URL_BASE_PATH="http://image.tmdb.org/t/p/w500//";
            String image_url = IMAGE_URL_BASE_PATH + posterPath;
            Picasso.get()
                    .load(image_url)
                    .placeholder(android.R.drawable.sym_def_app_icon)
                    .error(android.R.drawable.sym_def_app_icon)
                    .into(mPoster);
        }

        if (intent.hasExtra("MovieTitle")){
            String movieTitle = intent.getStringExtra("MovieTitle");
            mTitle.setText(movieTitle);
            favorite.setTitle(movieTitle);
        }
        if (intent.hasExtra("MovieReleaseDate")){
            String releaseDate = intent.getStringExtra("MovieReleaseDate");
            mMovieReleaseDate.setText(releaseDate);
            favorite.setReleaseDate(releaseDate);
        }
        if (intent.hasExtra("MovieDetails")){
            String movieDets = intent.getStringExtra("MovieDetails");
            mMovieDesc.setText(movieDets);
            favorite.setOverview(movieDets);
        }
        if (intent.hasExtra("MovieRating")){
            String rating = intent.getStringExtra("MovieRating");
            mMovieRating.setText(rating);
            favorite.setVoteAverage(Double.parseDouble(rating));
        }
        if (intent.hasExtra("MovieId")){
            int movieId = intent.getIntExtra("MovieId", 0);
            getTrailer(movieId);
            getReviews(movieId);
            favorite.setId(movieId);
        }

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ContentValues contentValues = new ContentValues();
//                contentValues.put(FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID, favorite.getId());
//                if (DatabaseUtils.checkMovieExist(mDb, favorite.getId())){
                Log.d("URI", getContentResolver().query(FavoritesContract.FavoritesEntry.CONTENT_URI,
                        null,
                        FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID,
                        new String[]{String.valueOf(favorite.getId())},
                        null).toString());

                if (getContentResolver().query(FavoritesContract.FavoritesEntry.CONTENT_URI,
                        null,
                        FavoritesContract.FavoritesEntry.COLUMN_MOVIE_ID,
                        new String[]{String.valueOf(favorite.getId())},
                        null) != null){
                    DatabaseUtils.deleteMovie(mDb, favorite);
                } else {
                    DatabaseUtils.insertMovie(mDb, favorite);
                }
            }
        });
    }

    // Makes the Asnyc call and creates/sets recyclerview and adapter
    public void getTrailer(int id){

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        MovieApiService movieApiService = retrofit.create(MovieApiService.class);

        Call<TrailerModel> call = movieApiService.getTrailer(id, API_KEY);
        Log.d("TRAILER", call.request().url().toString());
        call.enqueue(new Callback<TrailerModel>() {
            @Override
            public void onResponse(Call<TrailerModel> call, Response<TrailerModel> response) {
                listOfTrailers = response.body().getResults();

                TrailerViewAdapter adapter = new TrailerViewAdapter(MovieDetailActivity.this, listOfTrailers);
                mTrailerView.setAdapter(adapter);

                LinearLayoutManager trailerLayoutManager = new LinearLayoutManager(MovieDetailActivity.this);
                trailerLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                mTrailerView.setLayoutManager(trailerLayoutManager);
                Log.d("NUMBER OF TRAILERS", String.valueOf(adapter.getItemCount()));
            }

            @Override
            public void onFailure(Call<TrailerModel> call, Throwable throwable) {
                Log.e("FAILURE API CALL", throwable.toString());
            }
        });
    }

    public void getReviews(int id){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        MovieApiService movieApiService = retrofit.create(MovieApiService.class);
        Call<ReviewsModel> call = movieApiService.getReviews(id, API_KEY);
        call.enqueue(new Callback<ReviewsModel>() {
            @Override
            public void onResponse(Call<ReviewsModel> call, Response<ReviewsModel> response) {
                reviewsModel = response.body().getResults();

                ReviewViewAdapter adapter = new ReviewViewAdapter(MovieDetailActivity.this, reviewsModel);
                mReviewView.setAdapter(adapter);

                LinearLayoutManager recyclerLayoutManager = new LinearLayoutManager(MovieDetailActivity.this);
                recyclerLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                mReviewView.setLayoutManager(recyclerLayoutManager);


            }

            @Override
            public void onFailure(Call<ReviewsModel> call, Throwable t) {
                Log.e("FAILURE API CALL", t.toString());
            }
        });
    }
}
