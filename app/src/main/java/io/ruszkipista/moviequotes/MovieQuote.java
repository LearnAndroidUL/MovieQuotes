package io.ruszkipista.moviequotes;

import android.content.Context;

public class MovieQuote {
    private String movie;
    private String quote;

    public MovieQuote(String movie, String quote) {
        this.movie = movie;
        this.quote = quote;
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
