package app.movie.tutorial.com.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import app.movie.tutorial.com.R;
import app.movie.tutorial.com.model.ListOfTrailers;

public class TrailerViewAdapter extends RecyclerView.Adapter<TrailerViewAdapter.TrailerHolder> {

    private List<ListOfTrailers> trailers;
    private Context mContext;

    public TrailerViewAdapter(Context context, List response){
        mContext = context;
        trailers = response;
    }

    public class TrailerHolder extends RecyclerView.ViewHolder {

        public TextView mTrailer;
        ListOfTrailers item;

        public TrailerHolder(View itemView) {
            super(itemView);

            mTrailer = (TextView) itemView.findViewById(R.id.mTrailerTitle);
        }

        void setData(ListOfTrailers instance){
            this.item = instance;
            mTrailer.setText(item.getName());
        }
    }

    @Override
    public TrailerViewAdapter.TrailerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_trailer_item, parent, false);
        return new TrailerHolder(view);
    }

    @Override
    public void onBindViewHolder(final TrailerHolder holder, final int position) {
        holder.setData(trailers.get(position));

        holder.mTrailer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + trailers.get(position).getYoutubeKey()));
                holder.itemView.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }
}
