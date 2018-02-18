package zhuangzhi.android.movies;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zhuangzhi.android.movies.network.Movie;
import zhuangzhi.android.movies.network.MovieDatabase;
import zhuangzhi.android.movies.network.Movies;

/**
 * Created by zhuangzhili on 2018-02-18.
 */

public class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {

    private final static String LOG_TAG = FetchMoviesTask.class.getCanonicalName();

    private MovieAdapter mAdapter;

    public FetchMoviesTask(MovieAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    protected List<Movie> doInBackground(String... strings) {
        String sortBy = strings[0];

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieDatabase database = retrofit.create(MovieDatabase.class);
        Call<Movies> moviesCall = database.fetchMovies(sortBy, BuildConfig.API_KEY);
        try {
            Movies movies = moviesCall.execute().body();
            return movies.getMovies();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Cannot fetch data from the Movie DB!");
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        if (movies != null) {
            mAdapter.addAll(movies);
        }
    }
}
