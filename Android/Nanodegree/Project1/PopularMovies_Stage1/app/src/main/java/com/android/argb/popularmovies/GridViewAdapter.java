package com.android.argb.popularmovies;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GridViewAdapter extends BaseAdapter {

    private Context mContext;

    private List<Movie> mMovies;

    public GridViewAdapter(Context c) {
        mContext = c;
        mMovies = new ArrayList<>();
    }

    public void addAll(List<Movie> s) {
        mMovies.addAll(s);
        this.notifyDataSetChanged();
    }

    public void clear() {
        mMovies.clear();
    }

    @Override
    public int getCount() {
        return mMovies.size();
    }

    @Override
    public Object getItem(int position) {
        return mMovies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mMovies.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setAdjustViewBounds(true);
        } else {
            imageView = (ImageView) convertView;
        }

        Picasso.with(mContext).load(mMovies.get(position).getCoverThumbnailURL()).into(imageView);
        return imageView;
    }
}