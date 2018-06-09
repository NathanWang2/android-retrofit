package app.movie.tutorial.com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import app.movie.tutorial.com.R;
import app.movie.tutorial.com.model.MovieAPIModel;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private List<MovieAPIModel> movies;
    private Context mContext;
    protected ItemListener mListener;
    public static final String IMAGE_URL_BASE_PATH="http://image.tmdb.org/t/p/w185//";

    public RecyclerViewAdapter(Context context, List values, ItemListener itemListener) {

        movies = values;
        mContext = context;
        mListener=itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mMovieTitle;
        public ImageView mPoster;
        public RelativeLayout relativeLayout;
        MovieAPIModel item;

        public ViewHolder(View v) {

            super(v);

            v.setOnClickListener(this);
            mMovieTitle = (TextView) v.findViewById(R.id.mMovieTitle);
            mPoster = (ImageView) v.findViewById(R.id.mPoster);
            relativeLayout = (RelativeLayout) v.findViewById(R.id.relativeLayout);

        }

        public void setData(MovieAPIModel item) {
            this.item = item;

            mMovieTitle.setText(item.getTitle());
            String image_url = IMAGE_URL_BASE_PATH + item.getPosterPath();
            Picasso.get()
                    .load(image_url)
                    .placeholder(android.R.drawable.sym_def_app_icon)
                    .error(android.R.drawable.sym_def_app_icon)
                    .into(mPoster);
        }


        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public interface ItemListener {
        void onItemClick(MovieAPIModel item);
    }
}
