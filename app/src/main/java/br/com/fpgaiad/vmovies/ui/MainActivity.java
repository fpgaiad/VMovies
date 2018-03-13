package br.com.fpgaiad.vmovies.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

    private static final String MOST_POPULAR_URL =
            Constants.QUERY_BASE_URL + Constants.MOST_POPULAR_STRING + Constants.API_KEY_WITH_SUFIX_BASE_URL;

    private static final String HIGHEST_RATED_URL =
            Constants.QUERY_BASE_URL + Constants.HIGHEST_RATED_STRING + Constants.API_KEY_WITH_SUFIX_BASE_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSpanCount();
        recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount);
        recyclerView.setLayoutManager(gridLayoutManager);

        recyclerView.setHasFixedSize(true);

        Log.d("MainActivity", MOST_POPULAR_URL);
        loadMovies(MOST_POPULAR_URL);
    }

    private void loadMovies(String url) {
        Ion.with(this)
                .load(url)
                .as(MovieResponse.class)
                .setCallback(new FutureCallback<MovieResponse>() {
                    @Override
                    public void onCompleted(Exception e, MovieResponse result) {
                        if (e == null) {
                            setResponse(result);
                        } else {
                            Toast.makeText(MainActivity.this,
                                    "No internet connection", Toast.LENGTH_LONG).show();
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
            intent.putExtra("movieExtra", movie);
            startActivity(intent);
        }
    }

    private void setSpanCount() {

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
        if (mToast != null) {
            mToast.cancel();
        }
        int itemThatWasClickedId = item.getItemId();

        boolean isPopularVideos = itemThatWasClickedId == R.id.action_popular;
        loadMovies(isPopularVideos ? MOST_POPULAR_URL : HIGHEST_RATED_URL);
        showMessage(isPopularVideos ? "Most popular" : "Highest rated");
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
}

