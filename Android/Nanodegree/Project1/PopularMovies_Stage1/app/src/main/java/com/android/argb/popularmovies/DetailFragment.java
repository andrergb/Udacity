package com.android.argb.popularmovies;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DetailFragment extends Fragment {

    private String mMovieID;

    private ImageView imgPoster;
    private TextView textMovieTitle;
    private TextView textMovieYear;
    private TextView textMovieRuntime;
    private TextView textMovieRating;
    private TextView textMovieSynopsis;

    public DetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        imgPoster = (ImageView) rootView.findViewById(R.id.image_poster);
        textMovieTitle = (TextView) rootView.findViewById(R.id.text_movie_title);
        textMovieYear = (TextView) rootView.findViewById(R.id.text_movie_year);
        textMovieRuntime = (TextView) rootView.findViewById(R.id.text_runtime);
        textMovieRating = (TextView) rootView.findViewById(R.id.text_rating);
        textMovieSynopsis = (TextView) rootView.findViewById(R.id.text_synopsis);


        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra("MOVIE_ID")) {
            mMovieID = intent.getStringExtra("MOVIE_ID");
        }

        updateMovie();

        return rootView;
    }

    private void updateMovie() {
        FetchMovieTask movieTask = new FetchMovieTask();
        movieTask.execute(mMovieID);
    }

    private class FetchMovieTask extends AsyncTask<String, Void, Movie> {

        ProgressDialog progressDialog;
        private String TAG = FetchMovieTask.class.getSimpleName();
        private String MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/";

        @Override
        protected Movie doInBackground(String... params) {
            String moviesJsonStr = "";
            String movieId = params[0];

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String language = "en-US";

            try {
                final String APP_KEY = "api_key";
                final String LANGUAGE_PARAM = "language";

                Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                        .appendPath(movieId)
                        .appendQueryParameter(APP_KEY, BuildConfig.THE_MOVIE_DB_API_KEY)
                        .appendQueryParameter(LANGUAGE_PARAM, language)
                        .build();
                URL url = new URL(builtUri.toString());

                //Log.d(TAG, "URL: " + url);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream == null) {
                    return null;
                }

                String line;
                StringBuilder buffer = new StringBuilder();
                reader = new BufferedReader(new InputStreamReader(inputStream));
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                    buffer.append("\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }

                moviesJsonStr = buffer.toString();

            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "Error ", e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getMovieDataFromJson(moviesJsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Movie result) {
            super.onPostExecute(result);

            if (result != null) {
                Picasso.with(getActivity()).load(result.getMoviePosterURL()).into(imgPoster);
                textMovieTitle.setText(result.getTitle());
                textMovieYear.setText(result.getReleaseDate().split("-")[0]);
                textMovieRuntime.setText(result.getRuntime().concat("min"));
                textMovieRating.setText(result.getVoteAverage().concat("/10"));
                textMovieSynopsis.setText(result.getSynopsis());
            }

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading Movie");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setGravity(Gravity.CENTER);
            progressDialog.show();
        }

        private Movie getMovieDataFromJson(String json) throws JSONException {
            Movie auxMovie = new Movie();

            final String MOVIE_TITLE = "title";
            final String MOVIE_RELEASE_DATE = "release_date";
            final String MOVIE_POSTER_URL = "poster_path";
            final String MOVIE_VOTE_AVG = "vote_average";
            final String MOVIE_SYNOPSIS = "overview";
            final String MOVIE_RUNTIME = "runtime";


            final String MOVIE_BASE_PATH = "http://image.tmdb.org/t/p/";
            final String MOVIE_BASE_QUALITY = "w342";

            JSONObject jsonMovie = new JSONObject(json);

            auxMovie.setTitle(jsonMovie.getString(MOVIE_TITLE));
            auxMovie.setReleaseDate(jsonMovie.getString(MOVIE_RELEASE_DATE));
            auxMovie.setMoviePosterURL(MOVIE_BASE_PATH + MOVIE_BASE_QUALITY + jsonMovie.getString(MOVIE_POSTER_URL));
            auxMovie.setVoteAverage(jsonMovie.getString(MOVIE_VOTE_AVG));
            auxMovie.setSynopsis(jsonMovie.getString(MOVIE_SYNOPSIS));
            auxMovie.setRuntime(jsonMovie.getString(MOVIE_RUNTIME));

            return auxMovie;
        }
    }
}
