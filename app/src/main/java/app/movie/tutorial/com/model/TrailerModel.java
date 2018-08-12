package app.movie.tutorial.com.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class TrailerModel {
    @SerializedName("id")
    private int id_trailer;
    @SerializedName("results")
    private List<TrailerModel> results;

    public int getIdTrailer(){
        return id_trailer;
    }

    public void setIdTrailer(int id_trailer){
        this.id_trailer=id_trailer;
    }

    public List<TrailerModel> getResults(){
        return results;
    }
    public void setResults(){
        this.results = results;
    }

}
