package zhuangzhi.android.movies.network;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by zhuangzhili on 2018-03-26.
 */

public class Reviews {

    @SerializedName("results")
    private ArrayList<Review> reviews = new ArrayList<>();

    public ArrayList<Review> getReviews() {
        return reviews;
    }


}
