package br.com.fpgaiad.vmovies.presentation;

import android.content.Context;
import android.content.res.Configuration;
import android.telecom.Call;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;

import javax.security.auth.callback.Callback;

import br.com.fpgaiad.vmovies.entities.Constants;
import br.com.fpgaiad.vmovies.entities.MovieResponse;
import br.com.fpgaiad.vmovies.repository.VMovieRespository;
import br.com.fpgaiad.vmovies.ui.VMovieView;

public class VMoviePresenterImpl implements VMoviePresenter {

    private VMovieView view;
    private VMovieRespository respository;
    private boolean isPopularVideosOnPresenter;
    private Callback callback;
    private retrofit2.Call<MovieResponse> call;


    public VMoviePresenterImpl(VMovieView view, VMovieRespository respository) {
        this.view = view;
        this.respository = respository;
    }


    @Override
    public void loadMovies(String url) {
        call = respository.getRequestedMovies(callback, url);
        view.showMessage(isPopularVideosOnPresenter ? "Most popular" : "Highest rated");
        callSetResponse((MovieResponse) call);
    }

    @Override
    public void setIsPopularVideoOnPresenter(boolean isPopularVideosFromView) {
        this.isPopularVideosOnPresenter = isPopularVideosFromView;
    }

    @Override
    public boolean checkIsPopularVideos(String movieToLoad) {
        return movieToLoad.equals(Constants.MOST_POPULAR_URL);
    }

    @Override
    public void callSetResponse(MovieResponse result) {
        view.setResponse(result);
    }

    @Override
    public String chooseMovies() {
        return isPopularVideosOnPresenter ?
                Constants.MOST_POPULAR_URL : Constants.HIGHEST_RATED_URL;
    }

}
