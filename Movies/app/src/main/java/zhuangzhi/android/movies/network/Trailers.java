package zhuangzhi.android.movies.network;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by zhuangzhili on 2018-03-26.
 */

public class Trailers {

    @SerializedName("results")
    private ArrayList<Trailer> trailers = new ArrayList<>();

    public ArrayList<Trailer> getTrailers() {
        return trailers;
    }

}
