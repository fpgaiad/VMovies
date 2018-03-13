package br.com.fpgaiad.vmovies.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.fpgaiad.vmovies.R;
import br.com.fpgaiad.vmovies.entities.Constants;
import br.com.fpgaiad.vmovies.entities.Movie;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    //Using 'Butter Knife' framework
    @BindView(R.id.iv_detail_poster) ImageView detailPoster;
    @BindView(R.id.tv_detail_title) TextView detailTitle;
    @BindView(R.id.tv_detail_date) TextView detailDate;
    @BindView(R.id.rb_detail_votes) RatingBar detailRatingBarVotes;
    @BindView(R.id.tv_detail_votes) TextView detailVotes;
    @BindView(R.id.tv_detail_vote_count) TextView detailVoteCount;
    @BindView(R.id.tv_detail_synopsis) TextView detailSynopsis;

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


        Intent intent = getIntent();
        if (intent.hasExtra("movieExtra")) {

            Movie movie = intent.getParcelableExtra("movieExtra");
            setDetails(movie);
        }
    }

    private void setDetails(Movie movieDetail) {

        String imageUrl = Constants.IMAGE_BASE_URL + movieDetail.getPosterPath();
        Picasso.with(this)
                .load(imageUrl)
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
