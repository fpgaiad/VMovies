package br.com.fpgaiad.vmovies.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import br.com.fpgaiad.vmovies.R;
import br.com.fpgaiad.vmovies.entities.Constants;
import br.com.fpgaiad.vmovies.entities.MovieResponse;

public class MainActivity extends AppCompatActivity implements ListAdapter.ClickListener {

    public int spanCount;
    RecyclerView recyclerView;
    private Toast mToast;
    private String mMovieQueryUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSpanCount();
        recyclerView = findViewById(R.id.recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        //recyclerView.setAdapter(new ListAdapter(this, new MovieRepository().getMovieResponse().getResults(), this));
        recyclerView.setHasFixedSize(true);

        mMovieQueryUrl = Constants.MOVIE_QUERY_BASE_URL + Constants.YOUR_API_KEY + "&language=en-US&sort_by=popularity.desc";

        connect();

    }

    private void connect() {
        Ion.with(this)
                .load(mMovieQueryUrl)
                .as(MovieResponse.class)
                .setCallback(new FutureCallback<MovieResponse>() {
                    @Override
                    public void onCompleted(Exception e, MovieResponse result) {
                        setResponse(result);
                    }
                });

    }

    public void setResponse(MovieResponse result) {
        recyclerView.setAdapter(new ListAdapter(this, result.getResults(), this ));
    }


    @Override
    public void onListItemClick(int clickedItemIndex) {
        if (mToast != null) {
            mToast.cancel();
        }
        String toastMessage = "Item #" + clickedItemIndex + "clicked";
        mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);
        mToast.show();

        //Call DetailActivity.class
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        startActivity(intent);
    }


    public void setSpanCount() {
        Display screenOrientation = getWindowManager().getDefaultDisplay();
        if (screenOrientation.getWidth() == screenOrientation.getHeight()) {
            spanCount = 3;
        } else if (screenOrientation.getWidth() < screenOrientation.getHeight()) {
            spanCount = 2;
        } else {
            spanCount = 3;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemThatWasClickedId = item.getItemId();

        if (itemThatWasClickedId == R.id.action_popular) {
            mMovieQueryUrl = Constants.MOVIE_QUERY_BASE_URL + Constants.YOUR_API_KEY + Constants.MOST_POPULAR_BASE_URL;
            connect();
            String textToShow = "sorting by most popular";
            Toast.makeText(this, textToShow, Toast.LENGTH_LONG).show();
            return true;

        } else if (itemThatWasClickedId == R.id.action_rated) {
            mMovieQueryUrl = Constants.MOVIE_QUERY_BASE_URL + Constants.YOUR_API_KEY + Constants.HIGHEST_RATED_BASE_URL;
            connect();
            String textToShow = "sorting by highest rated";
            Toast.makeText(this, textToShow, Toast.LENGTH_LONG).show();
        } else {

        }
        return super.onOptionsItemSelected(item);
    }
}

