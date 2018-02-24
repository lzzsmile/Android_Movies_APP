package zhuangzhi.android.movies;

import zhuangzhi.android.movies.network.Movie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;


/**
 * Created by zhuangzhili on 2018-02-21.
 */

public class DetailActivity extends AppCompatActivity {

    private ImageView moviePoster;
    private TextView movieTitle;
    private TextView movieReleaseDate;
    private TextView movieVoteAverage;
    private TextView movieSynopsis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        moviePoster = findViewById(R.id.detail_movie_poster);
        movieTitle = findViewById(R.id.detail_movie_title);
        movieReleaseDate = findViewById(R.id.detail_movie_release_date);
        movieVoteAverage = findViewById(R.id.detail_movie_vote_average);
        movieSynopsis = findViewById(R.id.detail_movie_synopsis);

        Intent intent = getIntent();
        if (intent.hasExtra("Movie")) {
            final Movie movie = intent.getExtras().getParcelable("Movie");
            populateUI(movie);
        } else {
            closeOnError();
        }
    }

    private void populateUI(Movie movie) {
        String posterUrl = movie.getPosterUrl();
        String title = movie.getTitle();
        String releaseDate = movie.getReleaseDate();
        Double userRating = movie.getUserRating();
        String overView = movie.getOverview();

        Picasso.with(this).load(posterUrl).into(moviePoster);
        movieTitle.setText(title);
        movieReleaseDate.setText(releaseDate);
        movieVoteAverage.setText(String.valueOf(userRating));
        movieSynopsis.setText(overView);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }
}
