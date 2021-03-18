package com.merchpandas.moviesapp.Utils;

import android.util.Log;

import com.merchpandas.moviesapp.POJO.Moviespojo;
import com.merchpandas.moviesapp.POJO.Reviewpojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MovieUtils {
    private static String LOG_ERROR = "MovieUtilsClass-Error";
    private static String LOG_OUTPUT = "MovieUtilsClass-Output";

    private MovieUtils() {
    }

    public static ArrayList<Reviewpojo> fetchMovieReview(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_ERROR, "Problem making the HTTP request.", e);
        }

        ArrayList<Reviewpojo> MovieData = fetchMovieReviewFromJson(jsonResponse);
        return MovieData;

    }

    public static ArrayList<String> fetchMovieVideo(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_ERROR, "Problem making the HTTP request.", e);
        }

        ArrayList<String> MovieData = fetchMovieVideoFromJson(jsonResponse);
        return MovieData;

    }

    public static ArrayList<Moviespojo> fetchMovieDetails(String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_ERROR, "Problem making the HTTP request.", e);
        }

        ArrayList<Moviespojo> MovieData = fetchMovieDataFromJson(jsonResponse);
        return MovieData;

    }


    //creating url
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_ERROR, "Problem building the URL ", e);
        }
        return url;
    }

    //making http request
    private static String makeHttpRequest(URL url) throws IOException {
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
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);

            } else {
                Log.e(LOG_ERROR, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_ERROR, "Problem retrieving the MovieData JSON results.", e);
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

    //changing stream of data in readable form
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    //All details of Movie
    public static ArrayList<Moviespojo> fetchMovieDataFromJson(String movieJson) {
        ArrayList<Moviespojo> arrayList = new ArrayList<>();
        try {
            JSONObject baseJsonObject = new JSONObject(movieJson);
            JSONArray jsonArray = baseJsonObject.getJSONArray("results");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("title");
                String date = jsonObject.getString("release_date");
                String overview = jsonObject.getString("overview");
                String rating = jsonObject.getString("vote_average");
                String image = jsonObject.getString("poster_path");
                String id = jsonObject.getString("id");

                Moviespojo movieDetails = new Moviespojo(title, date, rating, overview, image, id);
                arrayList.add(movieDetails);

            }

        } catch (JSONException e) {
            Log.e(LOG_ERROR, "Error fetching features from json data" + e);
        }


        return arrayList;
    }

    //All Video Link of Movie
    public static ArrayList<String> fetchMovieVideoFromJson(String movieJson) {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            JSONObject baseJsonObject = new JSONObject(movieJson);
            JSONArray jsonArray = baseJsonObject.getJSONArray("results");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String key = jsonObject.getString("key");
                String link = "https://www.youtube.com/watch?v=" + key;
                arrayList.add(link);


            }

        } catch (JSONException e) {
            Log.e(LOG_ERROR, "Error fetching features from json data" + e);
        }


        return arrayList;
    }

    //All Review of Movie
    public static ArrayList<Reviewpojo> fetchMovieReviewFromJson(String movieJson) {
        ArrayList<Reviewpojo> arrayList = new ArrayList<>();
        try {
            JSONObject baseJsonObject = new JSONObject(movieJson);
            JSONArray jsonArray = baseJsonObject.getJSONArray("results");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String author = jsonObject.getString("author");
                String content = jsonObject.getString("content");
                Reviewpojo movieReview = new Reviewpojo(author, content);

                arrayList.add(movieReview);

                Log.i(LOG_OUTPUT, author);
                Log.i(LOG_OUTPUT, content);

            }

        } catch (JSONException e) {
            Log.e(LOG_ERROR, "Error fetching features from json data" + e);
        }


        return arrayList;
    }

}
