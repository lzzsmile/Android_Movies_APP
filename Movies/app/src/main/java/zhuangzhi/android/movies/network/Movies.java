package zhuangzhi.android.movies.network;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuangzhili on 2018-02-16.
 */

public class Movies {
    @SerializedName("results")
    private ArrayList<Movie> movies = new ArrayList<>();

    public ArrayList<Movie> getMovies() {
        return movies;
    }
}
