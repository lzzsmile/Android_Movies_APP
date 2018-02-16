package zhuangzhi.android.movies.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by zhuangzhili on 2018-02-06.
 */

public class NetworkUtils {
    private static final String LOG_TAG = NetworkUtils.class.getCanonicalName();

    public static URL buildUrl(String apiKey, String filterType) {
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("http")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(filterType)
                .appendQueryParameter("api_key", apiKey);
        URL url = null;
        try {
            url = new URL(uriBuilder.build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String buildPosterUrl(String posterPath) {
        return "http://image.tmdb.org/t/p/w500/" + posterPath;
    }

    public static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                StringBuilder stringBuilder = new StringBuilder();
                if (inputStream != null) {
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    String line = reader.readLine();
                    while(line != null) {
                        stringBuilder.append(line);
                        line = reader.readLine();
                    }
                }
                jsonResponse = stringBuilder.toString();
            } else {
                Log.e(LOG_TAG, "Error response code.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }
}
