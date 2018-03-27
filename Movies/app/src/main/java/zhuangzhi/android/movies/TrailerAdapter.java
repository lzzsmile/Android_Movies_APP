package zhuangzhi.android.movies;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import zhuangzhi.android.movies.network.Trailer;

/**
 * Created by zhuangzhili on 2018-03-27.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private ArrayList<Trailer> trailers;

    public TrailerAdapter(ArrayList<Trailer> trailers) {this.trailers = trailers;}

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = R.layout.trailer_list_item;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layoutId, parent, false);
        TrailerViewHolder viewHolder = new TrailerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        final Trailer trailer = trailers.get(position);
        String thumbnailURL = "http://img.youtube.com/vi/" + trailer.getKey() + "/0.jpg";
        Picasso.with(holder.trailerThumbnail.getContext())
                .load(thumbnailURL)
                .fit()
                .placeholder(R.drawable.movie_poster_holder)
                .into(holder.trailerThumbnail);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getContext().startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse(trailer.getTrailerURL())));
            }
        });
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public void addAll(List<Trailer> data) {
        if (trailers.size() > 0) {
            trailers.clear();
        }
        trailers.addAll(data);
        notifyDataSetChanged();
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder {
        private ImageView trailerThumbnail;
        private View mView;

        TrailerViewHolder(View itemView) {
            super(itemView);
            this.trailerThumbnail = itemView.findViewById(R.id.trailer_thumbnail);
            this.mView = itemView;
        }
    }

}
