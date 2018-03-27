package zhuangzhi.android.movies;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zhuangzhi.android.movies.network.MovieDatabase;
import zhuangzhi.android.movies.network.Trailer;
import zhuangzhi.android.movies.network.Trailers;

/**
 * Created by zhuangzhili on 2018-03-27.
 */

public class FetchTrailersTask extends AsyncTask<Long, Void, ArrayList<Trailer>> {

    private static final String LOG_TAG = FetchTrailersTask.class.getCanonicalName();

    private TrailerAdapter mAdapter;

    public FetchTrailersTask (TrailerAdapter adapter) {mAdapter = adapter;}

    @Override
    protected ArrayList<Trailer> doInBackground(Long... longs) {
        Long movieId = longs[0];
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MovieDatabase database = retrofit.create(MovieDatabase.class);
        Call<Trailers> trailersCall = database.findTrailersById(movieId, BuildConfig.API_KEY);
        try {
            Trailers trailers = trailersCall.execute().body();
            return trailers.getTrailers();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Cannot fetch trailers from the Movie DB!");
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Trailer> trailers) {
        if (trailers != null) {
            mAdapter.addAll(trailers);
        }
    }
}
