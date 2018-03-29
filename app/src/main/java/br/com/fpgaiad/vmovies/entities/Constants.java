package br.com.fpgaiad.vmovies.entities;


import br.com.fpgaiad.vmovies.BuildConfig;

public class Constants {
    public static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185/";
    private static final String MOST_POPULAR_STRING = "popular?api_key=";
    private static final String HIGHEST_RATED_STRING = "top_rated?api_key=";
    private static final String SUFIX_BASE_URL = "&language=en-US&page=1";
    private static final String QUERY_BASE_URL = "https://api.themoviedb.org/3/movie/";

    //Create your own YOUR_API_KEY at: https://www.themoviedb.org/settings/api
    private static final String YOUR_API_KEY = BuildConfig.API_KEY;
    private static final String API_KEY_WITH_SUFIX_BASE_URL = YOUR_API_KEY + SUFIX_BASE_URL;

    public static final String MOST_POPULAR_URL = QUERY_BASE_URL + MOST_POPULAR_STRING +
            API_KEY_WITH_SUFIX_BASE_URL;

    public static final String HIGHEST_RATED_URL = QUERY_BASE_URL + HIGHEST_RATED_STRING +
            API_KEY_WITH_SUFIX_BASE_URL;
}
