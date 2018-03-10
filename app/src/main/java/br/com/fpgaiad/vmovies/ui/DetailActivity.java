package br.com.fpgaiad.vmovies.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.fpgaiad.vmovies.R;
import br.com.fpgaiad.vmovies.entities.Constants;
import br.com.fpgaiad.vmovies.entities.Movie;
import br.com.fpgaiad.vmovies.entities.MovieResponse;

public class DetailActivity extends AppCompatActivity {

    private int index;
    ImageView detailPoster;
    TextView detailTitle;
    TextView detailDate;
    RatingBar detailRatingBarVotes;
    TextView detailVotes;
    TextView detailSynopsis;
    TextView detailVoteCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailPoster = findViewById(R.id.iv_detail_poster);
        detailTitle = findViewById(R.id.tv_detail_title);
        detailDate = findViewById(R.id.tv_detail_date);
        detailRatingBarVotes = findViewById(R.id.rb_detail_votes);
        detailVotes = findViewById(R.id.tv_detail_votes);
        detailSynopsis = findViewById(R.id.tv_detail_synopsis);
        detailVoteCount = findViewById(R.id.tv_detail_vote_count);

        Intent intent = getIntent();

        if (intent.hasExtra("movieExtra")) {

            Movie movie = (Movie) intent.getSerializableExtra("movieExtra");
            setDetails(movie);

//            String indexStringPassed = intent.getStringExtra(Intent.EXTRA_TEXT);
//            index = Integer.valueOf(indexStringPassed);

//            Ion.with(this)
//                    .load(MainActivity.mMovieQueryUrl)
//                    .as(MovieResponse.class)
//                    .setCallback(new FutureCallback<MovieResponse>() {
//                        @Override
//                        public void onCompleted(Exception e, MovieResponse result) {
//                            setDetails(result);
//                        }
//                    });

        }
    }

    public void setDetails(Movie movieDetail) {
//        List<Movie> detailMovies = result.getMovies();
//        Movie movieDetail = detailMovies.get(index);

        String imageUrl = movieDetail.getPosterPath();
        Picasso.with(this)
                .load(Constants.IMAGE_BASE_URL + imageUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(detailPoster);

        float voteAverage = movieDetail.getVoteAverage() / 2;
        String stringVoteAverage = voteAverage + "  ";
        String stringVoteCount = movieDetail.getVoteCount() + " votes";


        detailTitle.setText(movieDetail.getOriginalTitle());
        detailDate.setText(movieDetail.getReleaseDate());
        detailSynopsis.setText(movieDetail.getOverview());
        detailRatingBarVotes.setRating(voteAverage);
        detailVotes.setText(stringVoteAverage);
        detailVoteCount.setText(stringVoteCount);

    }
}
