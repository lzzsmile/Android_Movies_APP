package zhuangzhi.android.movies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import zhuangzhi.android.movies.data.MovieContract.MovieEntry;

/**
 * Created by zhuangzhili on 2018-03-26.
 */

public class MovieDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String CREATE_TABLE_MOVIES = "CREATE TABLE " +
                MovieEntry.TABLE_NAME + "(" +
                MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL," +
                MovieEntry.COLUMN_MOVIE_TITLE + " TEXT NOT NULL," +
                MovieEntry.COLUMN_MOVIE_DESCRIPTION + " TEXT NOT NULL," +
                MovieEntry.COLUMN_MOVIE_POSTER_PATH + " TEXT NOT NULL," +
                MovieEntry.COLUMN_MOVIE_RELEASE_DATE + " TEXT NOT NULL," +
                MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE + " LONG NOT NULL" +
                ");";


        sqLiteDatabase.execSQL(CREATE_TABLE_MOVIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
