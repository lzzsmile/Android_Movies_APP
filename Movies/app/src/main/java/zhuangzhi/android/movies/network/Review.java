package zhuangzhi.android.movies.network;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhuangzhili on 2018-03-26.
 */

public class Review implements Parcelable {

    private static final String LOG_TAG = Review.class.getSimpleName();

    @SerializedName("id")
    private String mId;
    @SerializedName("author")
    private String mAuthor;
    @SerializedName("content")
    private String mContent;
    @SerializedName("url")
    private String mUrl;

    public String getContent() {
        return mContent;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getUrl() {
        return mUrl;
    }

    private Review (Parcel source) {
        this.mId = source.readString();
        this.mAuthor = source.readString();
        this.mContent = source.readString();
        this.mUrl = source.readString();
    }

    public static final Parcelable.Creator<Review> CREATOR = new Creator<Review>() {
        public Review createFromParcel(Parcel source) {
            Review review = new Review(source);
            return review;
        }

        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(mId);
        parcel.writeString(mAuthor);
        parcel.writeString(mContent);
        parcel.writeString(mUrl);
    }

}
