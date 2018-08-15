package app.movie.tutorial.com.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TrailerModel {
    @SerializedName("id")
    private int movieId;
    @SerializedName("results")
    private List<ListOfTrailers> results;

    TrailerModel (int id, List<ListOfTrailers> response){
        this.movieId = id;
        this.results = response;
    }

    public int getIdTrailer(){
        return movieId;
    }

    public void setIdTrailer(int id_trailer){
        this.movieId=id_trailer;
    }

    public List<ListOfTrailers> getResults(){
        return results;
    }

    public void setResults(){
        this.results = results;
    }

}
