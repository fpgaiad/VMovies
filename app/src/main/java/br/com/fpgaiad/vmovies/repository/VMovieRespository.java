package br.com.fpgaiad.vmovies.repository;

import javax.security.auth.callback.Callback;

import br.com.fpgaiad.vmovies.entities.MovieResponse;
import retrofit2.Call;

/**
 * Created by felipegaiad on 27/03/18.
 */

public interface VMovieRespository {

    Call<MovieResponse> getRequestedMovies(Callback callback, String url);

}
