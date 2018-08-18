package app.movie.tutorial.com.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import app.movie.tutorial.com.R;
import app.movie.tutorial.com.adapter.ReviewViewAdapter;
import app.movie.tutorial.com.adapter.TrailerViewAdapter;
import app.movie.tutorial.com.model.ListOfTrailers;
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

        Intent intent = getIntent();

        if (intent.hasExtra("MoviePoster")){
            String IMAGE_URL_BASE_PATH="http://image.tmdb.org/t/p/w500//";
            String image_url = IMAGE_URL_BASE_PATH + intent.getStringExtra("MoviePoster");
            Picasso.get()
                    .load(image_url)
                    .placeholder(android.R.drawable.sym_def_app_icon)
                    .error(android.R.drawable.sym_def_app_icon)
                    .into(mPoster);
        }

        if (intent.hasExtra("MovieTitle")){
            mTitle.setText(intent.getStringExtra("MovieTitle"));
        }
        if (intent.hasExtra("MovieReleaseDate")){
            mMovieReleaseDate.setText(intent.getStringExtra("MovieReleaseDate"));
        }
        if (intent.hasExtra("MovieDetails")){
            mMovieDesc.setText(intent.getStringExtra("MovieDetails"));
        }
        if (intent.hasExtra("MovieRating")){
            mMovieRating.setText(intent.getStringExtra("MovieRating"));
        }
        if (intent.hasExtra("MovieId")){
            int movieId = intent.getIntExtra("MovieId", 0);
            getTrailer(movieId);
            getReviews(movieId);
        }
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
                Toast.makeText(MovieDetailActivity.this, "This Passed", Toast.LENGTH_SHORT).show();

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
                String failureText = "This call has failed!";
                Toast.makeText(MovieDetailActivity.this, failureText, Toast.LENGTH_SHORT).show();
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
