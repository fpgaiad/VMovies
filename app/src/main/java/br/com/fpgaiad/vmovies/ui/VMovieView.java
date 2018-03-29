package br.com.fpgaiad.vmovies.ui;

import br.com.fpgaiad.vmovies.entities.MovieResponse;

/**
 * Created by felipegaiad on 26/03/18.
 */

public interface VMovieView {

    void setResponse(MovieResponse result);

    void showMessage(String textToShow);

    void setSpanCount();

}
