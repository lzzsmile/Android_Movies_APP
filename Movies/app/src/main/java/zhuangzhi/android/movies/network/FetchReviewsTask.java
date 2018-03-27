package zhuangzhi.android.movies.network;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import zhuangzhi.android.movies.BuildConfig;
import zhuangzhi.android.movies.adapter.ReviewAdapter;

/**
 * Created by zhuangzhili on 2018-03-26.
 */

public class FetchReviewsTask extends AsyncTask<Long, Void, ArrayList<Review>> {

    private final static String LOG_TAG = FetchReviewsTask.class.getCanonicalName();

    private ReviewAdapter mAdapter;

    public FetchReviewsTask (ReviewAdapter adapter) {mAdapter = adapter;}

    @Override
    protected ArrayList<Review> doInBackground(Long... longs) {
        long movieId = longs[0];
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MovieDatabase database = retrofit.create(MovieDatabase.class);
        Call<Reviews> reviewsCall = database.findReviewsById(movieId, BuildConfig.API_KEY);
        try {
            Reviews reviews = reviewsCall.execute().body();
            return reviews.getReviews();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Cannot fetch reviews from the Movie DB!");
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Review> reviews) {
        if (reviews != null) {
            mAdapter.addAll(reviews);
        }
    }
}
