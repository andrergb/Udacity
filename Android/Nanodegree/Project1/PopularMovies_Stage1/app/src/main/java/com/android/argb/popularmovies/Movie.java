package com.android.argb.popularmovies;

public class Movie {

    private int id;
    private String coverThumbnailURL;

    private String title;
    private String releaseDate;
    private String moviePosterURL;
    private String voteAverage;
    private String synopsis;
    private String runtime;

    public Movie() {

    }

    public String getCoverThumbnailURL() {
        return coverThumbnailURL;
    }

    public void setCoverThumbnailURL(String coverThumbnailURL) {
        this.coverThumbnailURL = coverThumbnailURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMoviePosterURL() {
        return moviePosterURL;
    }

    public void setMoviePosterURL(String moviePosterURL) {
        this.moviePosterURL = moviePosterURL;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }
}
