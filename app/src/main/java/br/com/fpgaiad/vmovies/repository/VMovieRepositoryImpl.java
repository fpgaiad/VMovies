package br.com.fpgaiad.vmovies.repository;

import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import javax.security.auth.callback.Callback;

import br.com.fpgaiad.vmovies.entities.MovieResponse;
import retrofit2.Call;


public class VMovieRepositoryImpl implements VMovieRespository {

    private Ion ion;
    private MovieResponse movieResponse;

    public VMovieRepositoryImpl(Ion ion) {
        this.ion = ion;
    }

    @Override
    public Call<MovieResponse> getRequestedMovies(Callback callback, String url) {
        requestIon(url);
        return (Call<MovieResponse>) movieResponse;
    }

    void requestIon(String url) {
        Ion.with(ion.getContext())
                .load(url)
                .as(MovieResponse.class)
                .setCallback(new FutureCallback<MovieResponse>() {
                    @Override
                    public void onCompleted(Exception e, MovieResponse result) {
                        if (e == null) {
                            movieResponse = result;
                        } else {
                            Toast.makeText(ion.getContext(),
                                    "No internet connection", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

}
