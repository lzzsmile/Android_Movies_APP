package zhuangzhi.android.movies.network;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhuangzhili on 2018-03-26.
 */

public class Trailer implements Parcelable {

    public static final String LOG_TAG = Trailer.class.getSimpleName();

    @SerializedName("id")
    private String mId;
    @SerializedName("key")
    private String mKey;
    @SerializedName("name")
    private String mName;
    @SerializedName("site")
    private String mSite;
    @SerializedName("size")
    private String mSize;

    public String getName() {
        return mName;
    }

    public String getKey() {
        return mKey;
    }

    public String getTrailerURL() {
        return "http://www.youtube.com/watch?v=" + mKey;
    }

    private Trailer(Parcel source) {
        this.mId = source.readString();
        this.mKey = source.readString();
        this.mName = source.readString();
        this.mSite = source.readString();
        this.mSize = source.readString();
    }

    public static final Parcelable.Creator<Trailer> CREATOR = new Creator<Trailer>() {
        public Trailer createFromParcel(Parcel source) {
            Trailer trailer = new Trailer(source);
            return trailer;
        }

        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(mId);
        parcel.writeString(mKey);
        parcel.writeString(mName);
        parcel.writeString(mSite);
        parcel.writeString(mSize);
    }

}
