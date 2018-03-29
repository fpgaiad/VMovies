package br.com.fpgaiad.vmovies.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.koushikdutta.ion.Ion;

import br.com.fpgaiad.vmovies.R;
import br.com.fpgaiad.vmovies.entities.Constants;
import br.com.fpgaiad.vmovies.entities.Movie;
import br.com.fpgaiad.vmovies.entities.MovieResponse;
import br.com.fpgaiad.vmovies.presentation.VMoviePresenter;
import br.com.fpgaiad.vmovies.presentation.VMoviePresenterImpl;
import br.com.fpgaiad.vmovies.repository.VMovieRepositoryImpl;
import br.com.fpgaiad.vmovies.repository.VMovieRespository;

public class MainActivity extends AppCompatActivity implements ListAdapter.ClickListener,
        VMovieView {

    private MovieResponse movieResponse;
    private Toast mToast = null;
    private RecyclerView recyclerView;
    private String movieToLoad;
    private VMoviePresenter presenter;
    private boolean isPopularVideos;
    private int spanCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Ion ion = (Ion) Ion.with(this);
        VMovieRespository respository = new VMovieRepositoryImpl(ion);

        presenter = new VMoviePresenterImpl(this, respository);
        recyclerView = findViewById(R.id.recycler_view);

        setSpanCount();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);

        if (savedInstanceState != null) {
            movieToLoad = savedInstanceState.getString("current_movie");
        } else {
            movieToLoad = Constants.MOST_POPULAR_URL;
        }

        isPopularVideos = presenter.checkIsPopularVideos(movieToLoad);
        presenter.setIsPopularVideoOnPresenter(isPopularVideos);
        presenter.loadMovies(movieToLoad);
    }
//    private void loadMovies(String url) {
//        Ion.with(this)
//                .load(url)
//                .as(MovieResponse.class)
//                .setCallback(new FutureCallback<MovieResponse>() {
//                    @Override
//                    public void onCompleted(Exception e, MovieResponse result) {
//                        if (e == null) {
//                            setResponse(result);
//                        } else {
//                            Toast.makeText(MainActivity.this,
//                                    "No internet connection", Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//
//    }

    @Override
    public void setResponse(MovieResponse result) {
        movieResponse = result;
        recyclerView.setAdapter(new ListAdapter(this, result.getMovies(), this));
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        if (movieResponse != null) {
            Movie movie = movieResponse.getMovies().get(clickedItemIndex);
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("movieExtra", movie);
            startActivity(intent);
        }
    }

    @Override
    public void setSpanCount() {
        spanCount = getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE ? 3 : 2;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemThatWasClickedId = item.getItemId();
        isPopularVideos = itemThatWasClickedId == R.id.action_popular;

        presenter.setIsPopularVideoOnPresenter(isPopularVideos);

        movieToLoad = presenter.chooseMovies();
        presenter.loadMovies(movieToLoad);
        return true;
        //Alternative Code:
//
//        if (itemThatWasClickedId == R.id.action_popular) {
//            loadMovies(MOST_POPULAR_URL);
//            showMessage("Most popular");
//            return true;
//
//        } else if (itemThatWasClickedId == R.id.action_rated) {
//            loadMovies(HIGHEST_RATED_URL);
//            showMessage("Highest rated");
//            return true;
//        } else {
//            return super.onOptionsItemSelected(item);
//        }
    }

    @Override
    public void showMessage(String textToShow) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(this, textToShow, Toast.LENGTH_LONG);
        mToast.show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("current_movie", movieToLoad);
    }
}

