package br.com.fpgaiad.vmovies.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.fpgaiad.vmovies.R;
import br.com.fpgaiad.vmovies.entities.Constants;
import br.com.fpgaiad.vmovies.entities.Movie;
import br.com.fpgaiad.vmovies.entities.ReviewResponse;
import br.com.fpgaiad.vmovies.entities.Trailer;
import br.com.fpgaiad.vmovies.entities.TrailerResponse;
import br.com.fpgaiad.vmovies.repository.FavoriteContract;
import butterknife.BindView;
import butterknife.ButterKnife;

import static br.com.fpgaiad.vmovies.repository.FavoriteContract.FavoriteEntry.CONTENT_URI;

public class DetailActivity extends AppCompatActivity {

    //Using 'Butter Knife' framework
    @BindView(R.id.iv_detail_poster)
    ImageView detailPoster;
    @BindView(R.id.tv_detail_title)
    TextView detailTitle;
    @BindView(R.id.tv_detail_date)
    TextView detailDate;
    @BindView(R.id.rb_detail_votes)
    RatingBar detailRatingBarVotes;
    @BindView(R.id.tv_detail_votes)
    TextView detailVotes;
    @BindView(R.id.tv_detail_vote_count)
    TextView detailVoteCount;
    @BindView(R.id.tv_detail_synopsis)
    TextView detailSynopsis;
    @BindView(R.id.recycler_view_reviews)
    RecyclerView recyclerViewReviews;
    @BindView(R.id.iv_favorite_empty)
    ImageView emptyFavorite;
    @BindView(R.id.iv_favorite_selected)
    ImageView selectedFavorite;
    @BindView(R.id.pb_review_loading_indicator)
    ProgressBar mReviewLoadingIndicator;

    private Movie movie;
    private Toast mFavToast;
    private boolean isFavorite = false;
    //private String movieId;

//Code used without 'Butter Knife':
//
//    private ImageView detailPoster;
//    private TextView detailTitle;
//    private TextView detailDate;
//    private RatingBar detailRatingBarVotes;
//    private TextView detailVotes;
//    private TextView detailSynopsis;
//    private TextView detailVoteCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //Using 'Butter Knife' framework
        ButterKnife.bind(this);
//Code used without 'Butter Knife':
//
//        detailPoster = findViewById(R.id.iv_detail_poster);
//        detailTitle = findViewById(R.id.tv_detail_title);
//        detailDate = findViewById(R.id.tv_detail_date);
//        detailRatingBarVotes = findViewById(R.id.rb_detail_votes);
//        detailVotes = findViewById(R.id.tv_detail_votes);
//        detailSynopsis = findViewById(R.id.tv_detail_synopsis);
//        detailVoteCount = findViewById(R.id.tv_detail_vote_count);
        recyclerViewReviews = findViewById(R.id.recycler_view_reviews);
        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));

        Intent intent = getIntent();
        if (intent.hasExtra(getString(R.string.movie_extra))) {

            movie = intent.getParcelableExtra(getString(R.string.movie_extra));
            setDetails(movie);
            setReviews(movie);
        }
    }

    public void setReviews(Movie movie) {

        mReviewLoadingIndicator.setVisibility(View.VISIBLE);

        String movieId = String.valueOf(movie.getId());
        String REVIEW_URL = Constants.QUERY_BASE_URL + movieId + Constants.REVIEWS_STRING +
                Constants.API_KEY_WITH_SUFIX_BASE_URL;

        Ion.with(this)
                .load(REVIEW_URL)
                .as(ReviewResponse.class)
                .setCallback(new FutureCallback<ReviewResponse>() {
                    @Override
                    public void onCompleted(Exception e, ReviewResponse reviewResult) {
                        mReviewLoadingIndicator.setVisibility(View.INVISIBLE);
                        if (e == null) {
                            setReviewResult(reviewResult);
                        } else {
                            Toast.makeText(DetailActivity.this,
                                    "No response", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void setReviewResult(ReviewResponse reviewResult) {
        recyclerViewReviews.setAdapter(new ReviewAdapter(this, reviewResult.getReviews()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setDetails(Movie movie) {

        String imageUrl = Constants.IMAGE_BASE_URL + movie.getPosterPath();
        Picasso.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(detailPoster);

        float voteAverage = movie.getVoteAverage() / 2;
        String stringVoteAverage = voteAverage + "  ";
        String stringVoteCount = "/ " + movie.getVoteCount() + " votes";

        detailTitle.setText(movie.getOriginalTitle());
        detailDate.setText(movie.getReleaseDate().substring(0, 4));
        detailSynopsis.setText(movie.getOverview());
        detailRatingBarVotes.setRating(voteAverage);
        detailVotes.setText(stringVoteAverage);
        detailVoteCount.setText(stringVoteCount);
    }

    public void loadTrailer(View view) {

        String movieId = String.valueOf(movie.getId());
        String VIDEO_URL = Constants.QUERY_BASE_URL + movieId + Constants.VIDEOS_STRING +
                Constants.API_KEY_WITH_SUFIX_BASE_URL;

        Ion.with(this)
                .load(VIDEO_URL)
                .as(TrailerResponse.class)
                .setCallback(new FutureCallback<TrailerResponse>() {
                    @Override
                    public void onCompleted(Exception e, TrailerResponse r) {
                        if (e == null) {
                            List<Trailer> result = r.getResults();
                            int trailerSelected = 0;
                            if (trailerSelected < result.size()) {
                                String key = result.get(trailerSelected).getKey();
                                String trailerUrl = Constants.YOUTUBE_BASE_URL + key;

                                Uri url = Uri.parse(trailerUrl);
                                Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, url);
                                startActivity(youtubeIntent);
                            } else {
                                Toast.makeText(DetailActivity.this,
                                        R.string.trailer_not_available, Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Toast.makeText(DetailActivity.this,
                                    R.string.try_again_later, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void toggleFavorite(View view) {

        if (mFavToast != null) {
            mFavToast.cancel();
        }

        if (!isFavorite) {
            view.setVisibility(View.GONE);
            selectedFavorite.setVisibility(View.VISIBLE);

// TODO:Error!
//            ContentValues contentValues = new ContentValues();
//            contentValues.put(FavoriteContract.FavoriteEntry.MOVIE_ID, String.valueOf(movie.getId()));
//
//            getContentResolver().insert(CONTENT_URI, contentValues);

            mFavToast = Toast.makeText(this,
                    "Added to Favorites", Toast.LENGTH_SHORT);
            mFavToast.show();

            isFavorite = true;

        } else {
            view.setVisibility(View.GONE);
            emptyFavorite.setVisibility(View.VISIBLE);

            mFavToast = Toast.makeText(this,
                    "Removed from Favorites", Toast.LENGTH_SHORT);
            mFavToast.show();

            isFavorite = false;
        }

    }
}
