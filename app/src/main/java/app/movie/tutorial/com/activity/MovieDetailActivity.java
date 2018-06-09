package app.movie.tutorial.com.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import app.movie.tutorial.com.R;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_info);

        ImageView mPoster = (ImageView) findViewById(R.id.PosterTV);
        TextView mTitle = (TextView) findViewById(R.id.MovieTitleTV);
        TextView mMovieDesc = (TextView) findViewById(R.id.MovieDescTV);
        TextView mMovieRating = (TextView) findViewById(R.id.rating);
        TextView mMovieReleaseDate = (TextView) findViewById(R.id.MovieReleaseDateTV);

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


    }
}
