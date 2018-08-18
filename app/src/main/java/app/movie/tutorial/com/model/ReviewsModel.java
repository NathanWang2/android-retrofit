package app.movie.tutorial.com.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReviewsModel {

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private ArrayList<ReviewList> results;

    @SerializedName("total_pages")
    private int total_pages;

    @SerializedName("total_results")
    private int total_results;

    public int getPage() {
        return page;
    }

    public ArrayList<ReviewList> getResults() {
        return results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public int getTotal_results() {
        return total_results;
    }

    public class ReviewList {

        @SerializedName("author")
        private String author;

        @SerializedName("content")
        private String content;

        public String getAuthor() {
            return author;
        }

        public String getReviewContent() {
            return content;
        }
    }
}
