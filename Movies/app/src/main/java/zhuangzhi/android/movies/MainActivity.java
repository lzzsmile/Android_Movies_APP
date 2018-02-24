package zhuangzhi.android.movies;

import android.content.res.Configuration;
import android.media.VolumeShaper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import zhuangzhi.android.movies.network.Movie;

public class MainActivity extends AppCompatActivity {

    private final static String LOG_TAG = MainActivity.class.getCanonicalName();

    private final static String SORT_TYPE_1 = "popular";
    private final static String SORT_TYPE_2 = "top_rated";
    private final static String SORT_TYPE_SELECTION = "selectedSortType";
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

        if (savedInstanceState == null) {
            fetchMovies(SORT_TYPE_1);
        } else {
            fillAdapterOnSelection(savedInstanceState.getInt(SORT_TYPE_SELECTION, R.id.action_popular));
        }

    }

    private int getGridColNum() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return 3;
        }
        return 2;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SORT_TYPE_SELECTION, sortType);
    }

    private void fetchMovies(String sortBy) {
        FetchMoviesTask moviesTask = new FetchMoviesTask(movieAdapter);
        moviesTask.execute(sortBy);
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
}
