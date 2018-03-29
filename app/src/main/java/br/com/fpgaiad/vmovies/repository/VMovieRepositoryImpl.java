package br.com.fpgaiad.vmovies.repository;

import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import br.com.fpgaiad.vmovies.entities.MovieResponse;
import br.com.fpgaiad.vmovies.presentation.VMoviePresenter;


public class VMovieRepositoryImpl implements VMovieRespository {

    private Ion ion;
    private VMoviePresenter presenter;

    public VMovieRepositoryImpl(Ion ion) {
        this.ion = ion;
    }

    @Override
    public void getRequestedMovies(String url) {
        Ion.with(ion.getContext())
                .load(url)
                .as(MovieResponse.class)
                .setCallback(new FutureCallback<MovieResponse>() {
                    @Override
                    public void onCompleted(Exception e, MovieResponse result) {
                        if (e == null) {
                            presenter.callSetResponse(result);
                        } else {
                            Toast.makeText(ion.getContext(),
                                    "No internet connection", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}
