package zhuangzhi.android.movies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import zhuangzhi.android.movies.network.Movie;

/**
 * Created by zhuangzhili on 2018-02-06.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movies;

    public MovieAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(layoutId, parent, false);
        MovieViewHolder viewHolder = new MovieViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        Picasso.with(holder.moviePoster.getContext())
                .load(movie.getPosterUrl())
                .placeholder(R.drawable.movie_poster_holder)
                .into(holder.moviePoster);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void addAll(List<Movie> data) {
        movies.addAll(data);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final ImageView moviePoster;

        MovieViewHolder(View itemView) {
            super(itemView);
            moviePoster = itemView.findViewById(R.id.movie_poster);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), DetailActivity.class);
            view.getContext().startActivity(intent);
        }
    }

}
