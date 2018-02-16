package zhuangzhi.android.movies.network;

import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhuangzhili on 2018-02-16.
 */

public class Movie {

    public static final String LOG_TAG = Movie.class.getCanonicalName();

    @SerializedName("id")
    private long mId;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("poster_path")
    private String mPosterPath;
    @SerializedName("overview")
    private String mOverview;
    @SerializedName("vote_average")
    private long mUserRating;
    @SerializedName("release_date")
    private String mReleaseDate;

    public Movie(long id, String title, String posterPath, String overview, long userRating, String releaseDate) {
        mId = id;
        mTitle = title;
        mPosterPath = posterPath;
        mOverview = overview;
        mUserRating = userRating;
        mReleaseDate = releaseDate;
    }

    public long getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPosterUrl() {
        return "http://image.tmdb.org/t/p/w500/" + mPosterPath;
    }

    public String getOverview() {
        return mOverview;
    }

    public long getUserRating() {
        return mUserRating;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }


}
