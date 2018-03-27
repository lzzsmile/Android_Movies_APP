package zhuangzhi.android.movies;

import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import zhuangzhi.android.movies.adapter.MovieAdapter;
import zhuangzhi.android.movies.data.MovieContract;
import zhuangzhi.android.movies.network.FetchMoviesTask;
import zhuangzhi.android.movies.network.Movie;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private final static String LOG_TAG = MainActivity.class.getCanonicalName();

    private final static String SORT_TYPE_1 = "popular";
    private final static String SORT_TYPE_2 = "top_rated";
    private final static String SORT_TYPE_3 = "favorite";
    private final static String SORT_TYPE_SELECTION = "selectedSortType";
    private final static int FAVORITE_LOADER_ID = 1;
    private int sortType = R.id.action_popular;

    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayoutManager layoutManager = new GridLayoutManager(this, getGridColNum());
        RecyclerView recyclerView = findViewById(R.id.recycler_movies);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        movieAdapter = new MovieAdapter(new ArrayList<Movie>());
        recyclerView.setAdapter(movieAdapter);

        if (savedInstanceState == null || !savedInstanceState.containsKey(SORT_TYPE_SELECTION)) {
            fetchMovies(SORT_TYPE_1);
        } else {
            fillAdapterOnSelection(savedInstanceState.getInt(SORT_TYPE_SELECTION, R.id.action_popular));
        }

    }

    private int getGridColNum() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width/widthDivider;
        if (nColumns < 2) return 2;
        return nColumns;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SORT_TYPE_SELECTION, sortType);
    }

    private void fetchMovies(String sortBy) {
        if (!sortBy.equals(SORT_TYPE_3)) {
            FetchMoviesTask moviesTask = new FetchMoviesTask(movieAdapter);
            moviesTask.execute(sortBy);
        } else {
            getSupportLoaderManager().initLoader(FAVORITE_LOADER_ID, null, this);
        }

    }

    private void fillAdapterOnSelection(Integer sort) {
        sortType = sort;
        switch (sortType) {
            case R.id.action_popular:
                fetchMovies(SORT_TYPE_1);
                break;
            case R.id.action_top_rated:
                fetchMovies(SORT_TYPE_2);
                break;
            case R.id.action_favorite:
                fetchMovies(SORT_TYPE_3);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        fillAdapterOnSelection(itemId);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                MovieContract.MovieEntry.COLUMN_MOVIE_ID,
                MovieContract.MovieEntry.COLUMN_MOVIE_TITLE,
                MovieContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH,
                MovieContract.MovieEntry.COLUMN_MOVIE_DESCRIPTION,
                MovieContract.MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE,
                MovieContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE
        };
        return new CursorLoader(
                this,
                MovieContract.MovieEntry.CONTENT_URI,
                projection,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        movieAdapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
