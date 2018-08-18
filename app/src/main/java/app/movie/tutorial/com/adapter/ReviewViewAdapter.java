package app.movie.tutorial.com.adapter;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import app.movie.tutorial.com.R;
import app.movie.tutorial.com.model.ReviewsModel;

public class ReviewViewAdapter extends RecyclerView.Adapter<ReviewViewAdapter.ReviewHolder> {

    private ArrayList<ReviewsModel.ReviewList> reviewList;
    private Context mContext;

    public ReviewViewAdapter(Context context, ArrayList<ReviewsModel.ReviewList> list){
        this.reviewList = list;
        this.mContext = context;
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_reviews, parent, false);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {
        holder.setData(reviewList.get(position));
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class ReviewHolder extends RecyclerView.ViewHolder {

        private TextView mAuthor;
        private TextView mContent;

        private ReviewHolder(View itemView) {
            super(itemView);
            mAuthor = (TextView) itemView.findViewById(R.id.reviewAuthor);
            mContent = (TextView) itemView.findViewById(R.id.reviewContent);
        }

        void setData (ReviewsModel.ReviewList item){
            mAuthor.setText(item.getAuthor());
            mContent.setText(item.getReviewContent());
        }
    }
}
