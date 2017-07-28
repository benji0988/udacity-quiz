package com.udacity.quiz.gbookapi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by nagabonar on 7/22/2017.
 */

public class QueryUtils {

    public static final String LOG_TAG = BookActivity.class.getSimpleName();

    protected static String author;

    protected static Context mContext;

    public QueryUtils(Context context){
        mContext = context;
    }

    protected static List<Book> fetchBookData(String searchText) {
        URL jsonUrl = createUrl(searchText);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(jsonUrl);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<Book> books = extractBooks(jsonResponse);
        return books;
    }

    protected static URL createUrl(String searchUrl) {
        URL url = null;
        try {
            url = new URL(searchUrl);
            Log.v(LOG_TAG, "URL created successfully");
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    protected static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpsURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        Log.v("Query Utils", jsonResponse);
        return jsonResponse;
    }

    private static String readFromStream(InputStream iStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (iStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(iStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    protected static List<Book> extractBooks(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        ArrayList<Book> books = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(jsonResponse);
            JSONArray items = root.getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {
                JSONObject details = items.getJSONObject(i);
                JSONObject volumeInfo = details.getJSONObject("volumeInfo");
                String title = volumeInfo.getString("title");
                String date = volumeInfo.getString("publishedDate");
                String desc;

                if(volumeInfo.has("description")) {
                    desc = volumeInfo.getString("description");
                }else{
                    desc = "N/A";
                }

                JSONArray checkAuthor = volumeInfo.optJSONArray("authors");
                if(checkAuthor != null ) {
                    JSONArray authorArray = volumeInfo.getJSONArray("authors");
                    author = authorArray.getString(0);
                }else{
                    author = "N/A";
                }

                JSONObject imagelink = volumeInfo.getJSONObject("imageLinks");
                String image = imagelink.getString("thumbnail");

                books.add(new Book(title, image, date, author, desc));
            }
        } catch (JSONException e) {
            Log.e("Query Utils", "Problem pasrsing the Book JSON results");
        }

        return books;
    }

    protected boolean CheckConnection(){
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnect = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        return  isConnect;
    }
}
