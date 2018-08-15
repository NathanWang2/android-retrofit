package app.movie.tutorial.com.model;

import com.google.gson.annotations.SerializedName;

public class ListOfTrailers {

    @SerializedName("id")
    private String movieId;

    @SerializedName("key")
    private String youtubeKey;

    @SerializedName("site")
    private String site;

    @SerializedName("name")
    private String name;

    ListOfTrailers(String movieId, String key, String site){
        this.movieId = movieId;
        this.youtubeKey = key;
        this.site = site;
    }

    public String getMovieId (){ return movieId; }
    public String getYoutubeKey () { return youtubeKey; }
    public String getSite () { return site; }
    public String getName () { return  name; }
}
