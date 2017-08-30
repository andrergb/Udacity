package com.android.argb.popularmovies;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {

    private GridViewAdapter mGridViewAdapter;
    private SharedPreferences prefs;

    private int totalPage = 1;
    private String currentSort;
    private boolean fetchingMovie = false;

    public MainFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        currentSort = prefs.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_key_default));

        updateMovies();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.homefragment, menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        mGridViewAdapter = new GridViewAdapter(getActivity());

        GridView gridview = (GridView) rootView.findViewById(R.id.grid_view);
        gridview.setAdapter(mGridViewAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent detailActivity = new Intent(getActivity(), DetailActivity.class);
                detailActivity.putExtra("MOVIE_ID", String.valueOf(((Movie) mGridViewAdapter.getItem(position)).getId()));
                startActivity(detailActivity);
            }
        });
        gridview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int lastInScreen = firstVisibleItem + visibleItemCount;
                if ((lastInScreen == totalItemCount) && !(fetchingMovie)) {
                    updateMovies();
                }
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }
        });
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            gridview.setNumColumns(2);
        } else {
            gridview.setNumColumns(4);
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        String sortOrder = prefs.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_key_default));
        if (!sortOrder.equalsIgnoreCase(currentSort)) {
            mGridViewAdapter.clear();
            totalPage = 1;
            currentSort = sortOrder;
            updateMovies();
        }
    }

    private void updateMovies() {
        FetchMoviesTask moviesTask = new FetchMoviesTask();
        moviesTask.execute(currentSort, String.valueOf(totalPage));
        totalPage++;
    }

    private class FetchMoviesTask extends AsyncTask<String, Void, List<Movie>> {

        ProgressDialog progressDialog;
        private String TAG = FetchMoviesTask.class.getSimpleName();
        private String MOVIE_BASE_URL = "https://api.themoviedb.org/3/movie/";

        @Override
        protected List<Movie> doInBackground(String... params) {
            fetchingMovie = true;
            String moviesJsonStr = "";
            String sortOrder = params[0];
            String currentPage = params[1];

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String language = "en-US";

            try {
                final String APP_KEY = "api_key";
                final String LANGUAGE_PARAM = "language";
                final String PAGE_PARAM = "page";

                Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                        .appendPath(sortOrder)
                        .appendQueryParameter(APP_KEY, BuildConfig.THE_MOVIE_DB_API_KEY)
                        .appendQueryParameter(LANGUAGE_PARAM, language)
                        .appendQueryParameter(PAGE_PARAM, currentPage)
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
                return getMoviesDataFromJson(moviesJsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<Movie> result) {
            super.onPostExecute(result);

            if (result != null)
                mGridViewAdapter.addAll(result);

            if (progressDialog.isShowing())
                progressDialog.dismiss();

            fetchingMovie = false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Fetching images");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setGravity(Gravity.CENTER);
            progressDialog.show();
        }

        private List<Movie> getMoviesDataFromJson(String json) throws JSONException {
            final String MOVIES_LIST = "results";
            final String MOVIES_POSTER_PATH = "poster_path";
            final String MOVIES_ID = "id";
            final String MOVIES_BASE_PATH = "http://image.tmdb.org/t/p/";
            final String MOVIES_BASE_QUALITY = "w185";

            JSONObject moviesJson = new JSONObject(json);
            JSONArray moviesArray = moviesJson.getJSONArray(MOVIES_LIST);

            List<Movie> tempMovies = new ArrayList<>();
            for (int i = 0; i < moviesArray.length(); i++) {
                JSONObject movie = moviesArray.getJSONObject(i);

                Movie auxMovie = new Movie();
                auxMovie.setId(movie.getInt(MOVIES_ID));
                auxMovie.setCoverThumbnailURL(MOVIES_BASE_PATH + MOVIES_BASE_QUALITY + movie.getString(MOVIES_POSTER_PATH));

                tempMovies.add(auxMovie);
            }

            return tempMovies;
        }
    }
}
