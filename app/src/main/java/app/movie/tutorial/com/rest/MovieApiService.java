package app.movie.tutorial.com.rest;

import app.movie.tutorial.com.model.ReviewsModel;
import app.movie.tutorial.com.model.TrailerModel;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.Call;
import app.movie.tutorial.com.model.MovieModel;

/**
 * Created by Gino Osahon on 13/03/2017.
 * Edited by Nathan Wang on 6/4/2018
 */
public interface MovieApiService {

    @GET("movie/top_rated")
    Call<MovieModel> getTopRatedMovies(
            @Query("api_key") String apiKey,
            @Query("page") int page);

    @GET("movie/popular")
    Call<MovieModel> getPoplarMovies(
            @Query("api_key") String apiKey,
            @Query("page") int page);

    @GET("movie/{movieId}/videos")
    Call<TrailerModel> getTrailer(
            @Path(value = "movieId", encoded = true) int id,
            @Query("api_key") String apiKey
    );

    @GET("movie/{movieId}/reviews")
    Call<ReviewsModel> getReviews(
        @Path(value = "movieId", encoded = true) int id,
        @Query("api_key") String apiKey
//        TODO Add page query
    );

}
