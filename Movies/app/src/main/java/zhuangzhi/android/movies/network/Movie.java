package zhuangzhi.android.movies.network;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhuangzhili on 2018-02-16.
 */

public class Movie implements Parcelable{

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
    private double mUserRating;
    @SerializedName("release_date")
    private String mReleaseDate;

    public Movie(long id, String title, String posterPath, String overview, double userRating, String releaseDate) {
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

    public double getUserRating() {
        return mUserRating;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    //The parcelable part
    private Movie(Parcel source) {
        this.mId = source.readLong();
        this.mTitle = source.readString();
        this.mPosterPath = source.readString();
        this.mOverview = source.readString();
        this.mUserRating = source.readDouble();
        this.mReleaseDate = source.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.mId);
        parcel.writeString(this.mTitle);
        parcel.writeString(this.mPosterPath);
        parcel.writeString(this.mOverview);
        parcel.writeDouble(this.mUserRating);
        parcel.writeString(this.mReleaseDate);
    }
}
