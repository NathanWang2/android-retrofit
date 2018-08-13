package app.movie.tutorial.com.model;

import com.google.gson.annotations.SerializedName;

public class TrailerResponse {

    @SerializedName("id")
    private int movieId;

    @SerializedName("key")
    private String youtubeKey;

    @SerializedName("site")
    private String site;

    TrailerResponse(int movieId, String key, String site){
        this.movieId = movieId;
        this.youtubeKey = key;
        this.site = site;
    }

    public int getMovieId (){ return movieId; }
    public String getYoutubeKey () { return youtubeKey; }
    public String site () { return site; }
}
