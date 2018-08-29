package app.movie.tutorial.com.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Gino Osahon on 13/03/2017.
 */

// This class contains all fetched movies and extra information
public class MovieModel {

    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private ArrayList<MovieAPIModel> results;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ArrayList<MovieAPIModel> getResults() {
        return results;
    }

    public void setResults(ArrayList<MovieAPIModel> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
