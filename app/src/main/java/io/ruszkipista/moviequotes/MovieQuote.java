package io.ruszkipista.moviequotes;

import android.content.Context;

public class MovieQuote {
    private String quote;
    private String movie;

    public MovieQuote(String quote, String movie) {
        this.quote = quote;
        this.movie = movie;
    }

    public MovieQuote(Context mContext) {

    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }
}
