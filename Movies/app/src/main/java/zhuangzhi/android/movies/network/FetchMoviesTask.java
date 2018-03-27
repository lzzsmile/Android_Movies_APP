package zhuangzhi.android.movies.network;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zhuangzhi.android.movies.BuildConfig;
import zhuangzhi.android.movies.adapter.MovieAdapter;

/**
 * Created by zhuangzhili on 2018-02-18.
 */

public class FetchMoviesTask extends AsyncTask<String, Void, ArrayList<Movie>> {

    private final static String LOG_TAG = FetchMoviesTask.class.getCanonicalName();

    private MovieAdapter mAdapter;

    public FetchMoviesTask(MovieAdapter adapter) {
        mAdapter = adapter;
    }

    @Override
    protected ArrayList<Movie> doInBackground(String... strings) {
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
            Log.e(LOG_TAG, "Cannot fetch movies from the Movie DB!");
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        if (movies != null) {
            mAdapter.addAll(movies);
        }
    }
}
