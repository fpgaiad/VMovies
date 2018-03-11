package br.com.fpgaiad.vmovies.entities;


import br.com.fpgaiad.vmovies.BuildConfig;

public class Constants {
    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185/";
    public static final String MOST_POPULAR_URL = "&language=en-US&sort_by=popularity.desc";
    public static final String HIGHEST_RATED_URL = "&language=en-US&sort_by=vote_average.desc";

    private static final String QUERY_BASE_URL =
            "https://api.themoviedb.org/3/discover/movie?api_key=";
    private static final String YOUR_API_KEY = BuildConfig.API_KEY;


    //Create your own YOUR_API_KEY at: https://www.themoviedb.org/settings/api
    public static final String BASE_URL_WITH_API_KEY = QUERY_BASE_URL + YOUR_API_KEY;
}
