package br.com.fpgaiad.vmovies.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import br.com.fpgaiad.vmovies.R;
import br.com.fpgaiad.vmovies.entities.Constants;
import br.com.fpgaiad.vmovies.entities.Movie;
import br.com.fpgaiad.vmovies.entities.MovieResponse;

public class MainActivity extends AppCompatActivity implements ListAdapter.ClickListener {

    private int spanCount;
    private MovieResponse movieResponse;
    private Toast mToast = null;
    private RecyclerView recyclerView;
    private String movieToLoad;
    private ProgressBar mMainLoadingIndicator;
    private ActionBar mActionBar;
    private boolean isPopularVideos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActionBar = this.getSupportActionBar();
        mMainLoadingIndicator = findViewById(R.id.pb_main_loading_indicator);

        setSpanCount();

        recyclerView = findViewById(R.id.recycler_view_thumbs);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setHasFixedSize(true);
//        if (savedInstanceState != null) {
//            movieToLoad = savedInstanceState.getString("current_movie");
//        } else {
//            movieToLoad = MOST_POPULAR_URL;
//        }
        movieToLoad = (savedInstanceState != null) ?
                savedInstanceState.getString(getString(R.string.current_movie_key)) : Constants.MOST_POPULAR_URL;
        isPopularVideos = movieToLoad != null && movieToLoad.equals(Constants.MOST_POPULAR_URL);

        loadMovies(movieToLoad);
    }

    private void loadMovies(String url) {
        mActionBar.setTitle(isPopularVideos ?
                getString(R.string.most_popular) : getString(R.string.highest_rated));
        mMainLoadingIndicator.setVisibility(View.VISIBLE);

        Ion.with(this)
                .load(url)
                .as(MovieResponse.class)
                .setCallback(new FutureCallback<MovieResponse>() {
                    @Override
                    public void onCompleted(Exception e, MovieResponse result) {
                        mMainLoadingIndicator.setVisibility(View.INVISIBLE);
                        if (e == null) {
                            setResponse(result);
                        } else {
                            Toast.makeText(MainActivity.this,
                                    "No response", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    private void setResponse(MovieResponse result) {
        movieResponse = result;
        recyclerView.setAdapter(new ListAdapter(this, result.getMovies(), this));
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

        if (movieResponse != null) {
            Movie movie = movieResponse.getMovies().get(clickedItemIndex);
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra(getString(R.string.movie_extra), movie);
            startActivity(intent);
        }
    }

    private void setSpanCount() {

        spanCount = getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE ? 4 : 2;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToast != null) {
            mToast.cancel();
        }
        int itemThatWasClickedId = item.getItemId();

        isPopularVideos = itemThatWasClickedId == R.id.action_popular;
        movieToLoad = isPopularVideos ? Constants.MOST_POPULAR_URL : Constants.HIGHEST_RATED_URL;
        loadMovies(movieToLoad);
        //showMessage(isPopularVideos ? getString(R.string.most_popular) : getString(R.string.highest_rated));
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

    private void showMessage(String textToShow) {
        mToast = Toast.makeText(this, textToShow, Toast.LENGTH_LONG);
        mToast.show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(getString(R.string.current_movie_key), movieToLoad);
    }
}

