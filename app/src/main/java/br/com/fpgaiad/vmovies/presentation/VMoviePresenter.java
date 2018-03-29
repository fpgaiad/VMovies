package br.com.fpgaiad.vmovies.presentation;

import br.com.fpgaiad.vmovies.entities.MovieResponse;

/**
 * Created by felipegaiad on 26/03/18.
 */

public interface VMoviePresenter {

    void loadMovies(String url);

    void setIsPopularVideoOnPresenter(boolean isPopularVideo);

    boolean checkIsPopularVideos(String movieToLoad);

    void callSetResponse(MovieResponse response);

    String chooseMovies();

}
