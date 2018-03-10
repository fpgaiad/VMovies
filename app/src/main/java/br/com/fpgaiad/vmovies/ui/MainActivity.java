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

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import br.com.fpgaiad.vmovies.R;
import br.com.fpgaiad.vmovies.entities.Constants;
import br.com.fpgaiad.vmovies.entities.Movie;
import br.com.fpgaiad.vmovies.entities.MovieResponse;

public class MainActivity extends AppCompatActivity implements ListAdapter.ClickListener {

    public int spanCount;
    private MovieResponse movieResponse;
    private Toast mToast = null;
    RecyclerView recyclerView;
    //public static String mMovieQueryUrl;
    public static final String MOST_POPULAR_URL = Constants.BASE_URL_WITH_API_KEY + Constants.MOST_POPULAR_URL;
    public static final String HIGHEST_RATED_URL = Constants.BASE_URL_WITH_API_KEY + Constants.HIGHEST_RATED_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSpanCount();
        recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        //recyclerView.setAdapter(new ListAdapter(this, new MovieRepository().getMovieResponse().getMovies(), this));
        recyclerView.setHasFixedSize(true);

        //mMovieQueryUrl = Constants.QUERY_BASE_URL + Constants.YOUR_API_KEY + "&language=en-US&sort_by=popularity.desc";

        loadMovies(MOST_POPULAR_URL);
    }

    private void loadMovies(String url) {
        Ion.with(this)
                .load(url)
                .as(MovieResponse.class)
                .setCallback(new FutureCallback<MovieResponse>() {
                    @Override
                    public void onCompleted(Exception e, MovieResponse result) {
                        setResponse(result);
                    }
                });

    }


    public void setResponse(MovieResponse result) {
        movieResponse = result;
        recyclerView.setAdapter(new ListAdapter(this, result.getMovies(), this ));
    }


    @Override
    public void onListItemClick(int clickedItemIndex) {
//        if (mToast != null) {
//            mToast.cancel();
//        }
//        String toastMessage = "Item #" + clickedItemIndex + "clicked";
//        mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);
//        mToast.show();

        if (movieResponse != null) {
            Movie movie = movieResponse.getMovies().get(clickedItemIndex);
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            //intent.putExtra(Intent.EXTRA_TEXT, String.valueOf(clickedItemIndex));
            intent.putExtra("movieExtra", movie);
            startActivity(intent);
        }



        //Call DetailActivity.class
//        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
//        intent.putExtra(Intent.EXTRA_TEXT, String.valueOf(clickedItemIndex));
//        startActivity(intent);
    }

    public void setSpanCount() {
//        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            spanCount = 3;
//        } else {
//            spanCount = 2;
//        }


        spanCount = getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE ? 3 : 2;



//        spanCount = 3;
//
//        Display screenOrientation = getWindowManager().getDefaultDisplay();
//        if (screenOrientation.getWidth() == screenOrientation.getHeight()) {
//            spanCount = 3;
//        } else if (screenOrientation.getWidth() < screenOrientation.getHeight()) {
//            spanCount = 2;
//        } else {
//            spanCount = 3;
//        }
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


//
//        boolean isPopularVideos = itemThatWasClickedId == R.id.action_popular;
//        loadMovies(isPopularVideos ? MOST_POPULAR_URL : HIGHEST_RATED_URL);
//        showMessage(isPopularVideos ? "Most popular" : "Highest rated");
//



        if (itemThatWasClickedId == R.id.action_popular) {
            //mMovieQueryUrl = Constants.QUERY_BASE_URL + Constants.YOUR_API_KEY + Constants.MOST_POPULAR_URL;
            loadMovies(MOST_POPULAR_URL);
            //String textToShow = "Most popular";

            showMessage("Most popular");
            return true;

        } else if (itemThatWasClickedId == R.id.action_rated) {
            //mMovieQueryUrl = Constants.QUERY_BASE_URL + Constants.YOUR_API_KEY + Constants.HIGHEST_RATED_URL;
            loadMovies(HIGHEST_RATED_URL);
            //String textToShow = "Highest rated";
            showMessage("Highest rated");
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void showMessage(String textToShow) {
        mToast = Toast.makeText(this, textToShow, Toast.LENGTH_LONG);
        mToast.show();
    }

    //    @Override
//    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
//    }

}

