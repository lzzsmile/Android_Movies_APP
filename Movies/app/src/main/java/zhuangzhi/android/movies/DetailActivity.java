package zhuangzhi.android.movies;

import zhuangzhi.android.movies.adapter.ReviewAdapter;
import zhuangzhi.android.movies.adapter.TrailerAdapter;
import zhuangzhi.android.movies.data.MovieContract;
import zhuangzhi.android.movies.data.MovieContract.MovieEntry;
import zhuangzhi.android.movies.network.FetchReviewsTask;
import zhuangzhi.android.movies.network.FetchTrailersTask;
import zhuangzhi.android.movies.network.Movie;
import zhuangzhi.android.movies.network.Review;
import zhuangzhi.android.movies.network.Trailer;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by zhuangzhili on 2018-02-21.
 */

public class DetailActivity extends AppCompatActivity {

    private final static String LOG_TAG = DetailActivity.class.getCanonicalName();

    private ImageView moviePoster;
    private TextView movieTitle;
    private TextView movieReleaseDate;
    private TextView movieVoteAverage;
    private TextView movieSynopsis;
    private RatingBar movieRating;
    private ImageView favIcon;

    private ReviewAdapter reviewAdapter;
    private TrailerAdapter trailerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        moviePoster = findViewById(R.id.detail_movie_poster);
        movieTitle = findViewById(R.id.detail_movie_title);
        movieReleaseDate = findViewById(R.id.detail_movie_release_date);
        movieVoteAverage = findViewById(R.id.detail_movie_vote_average);
        movieSynopsis = findViewById(R.id.detail_movie_synopsis);
        movieRating = findViewById(R.id.detail_rating_bar);
        favIcon = findViewById(R.id.favorite_image);

        RecyclerView recyclerViewReview = findViewById(R.id.review_list);
        RecyclerView recyclerViewTrailer = findViewById(R.id.trailer_list);
        LinearLayoutManager layoutManagerReview = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManagerTrailer = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewReview.setLayoutManager(layoutManagerReview);
        recyclerViewReview.setHasFixedSize(true);
        recyclerViewTrailer.setLayoutManager(layoutManagerTrailer);
        recyclerViewTrailer.setHasFixedSize(true);
        reviewAdapter = new ReviewAdapter(new ArrayList<Review>());
        trailerAdapter = new TrailerAdapter(new ArrayList<Trailer>());
        recyclerViewReview.setAdapter(reviewAdapter);
        recyclerViewTrailer.setAdapter(trailerAdapter);

        Intent intent = getIntent();
        if (intent.hasExtra("Movie")) {
            final Movie movie = intent.getExtras().getParcelable("Movie");
            favIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!movieIsInDB(movie)) {
                        changeToFavorite();
                        saveMovieToDB(movie);
                    } else {
                        removeFromFavorite();
                        deleteMovieFromDB(movie);
                    }
                }
            });
            populateUI(movie);
        } else {
            closeOnError();
        }
    }

    private void populateUI(Movie movie) {
        if (movieIsInDB(movie)) {
            changeToFavorite();
        }
        String posterUrl = movie.getPosterUrl();
        String title = movie.getTitle();
        String releaseDate = movie.getReleaseDate();
        double userRating = movie.getUserRating();
        String overView = movie.getOverview();
        String rating = "IMDB: " + String.valueOf(userRating) + "/10";

        Picasso.with(this).load(posterUrl).into(moviePoster);
        movieTitle.setText(title);
        movieReleaseDate.setText(releaseDate);
        movieVoteAverage.setText(rating);
        movieSynopsis.setText(overView);
        movieRating.setRating(Float.valueOf(String.valueOf(userRating)));

        fetchReviews(movie);
        fetchTrailers(movie);
    }

    private boolean movieIsInDB(Movie movie) {
        Cursor cursor = getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int movieId = cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID));
                if (movieId == movie.getId()) {
                    return true;
                }
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return false;
    }

    private void changeToFavorite() {
        favIcon.setImageResource(R.drawable.ic_favorite_black_24dp);
    }

    private void removeFromFavorite() {
        favIcon.setImageResource(R.drawable.ic_favorite_border_black_24dp);
    }

    private void saveMovieToDB(Movie movie) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieEntry.COLUMN_MOVIE_ID, movie.getId());
        contentValues.put(MovieEntry.COLUMN_MOVIE_TITLE, movie.getTitle());
        contentValues.put(MovieEntry.COLUMN_MOVIE_POSTER_PATH, movie.getPosterUrl());
        contentValues.put(MovieEntry.COLUMN_MOVIE_DESCRIPTION, movie.getOverview());
        contentValues.put(MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE, movie.getUserRating());
        contentValues.put(MovieEntry.COLUMN_MOVIE_RELEASE_DATE, movie.getReleaseDate());
        getContentResolver().insert(MovieEntry.CONTENT_URI, contentValues);
    }

    private void deleteMovieFromDB(Movie movie) {
        String selection = MovieEntry.COLUMN_MOVIE_ID + "=?";
        String[] selectionArgs = {String.valueOf(movie.getId())};
        getContentResolver().delete(MovieEntry.CONTENT_URI, selection, selectionArgs);
    }

    private void fetchReviews(Movie movie) {
        FetchReviewsTask task = new FetchReviewsTask(reviewAdapter);
        task.execute(movie.getId());
    }

    private void fetchTrailers(Movie movie) {
        FetchTrailersTask task = new FetchTrailersTask(trailerAdapter);
        task.execute(movie.getId());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }
}
