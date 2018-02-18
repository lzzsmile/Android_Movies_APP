package zhuangzhi.android.movies;

import android.content.res.Configuration;
import android.media.VolumeShaper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import zhuangzhi.android.movies.network.Movie;

public class MainActivity extends AppCompatActivity {

    private final static String LOG_TAG = MainActivity.class.getCanonicalName();

    private final static String SORT_TYPE_1 = "popular";
    private final static String SORT_TYPE_2 = "top_rated";

    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayoutManager layoutManager = new GridLayoutManager(this, getGridColNum());
        recyclerView = findViewById(R.id.recycler_movies);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        movieAdapter = new MovieAdapter(new ArrayList<Movie>());
        recyclerView.setAdapter(movieAdapter);

    }

    private int getGridColNum() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return 3;
        }
        return 2;
    }
}
