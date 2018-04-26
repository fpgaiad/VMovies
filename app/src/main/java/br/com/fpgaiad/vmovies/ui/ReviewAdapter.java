package br.com.fpgaiad.vmovies.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.fpgaiad.vmovies.R;
import br.com.fpgaiad.vmovies.entities.Review;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private final Context context;
    private final List<Review> reviewList;

    public ReviewAdapter(Context context, List<Review> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Context context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviewList.get(position);
        String authorLabel = "By :  " + review.getAuthor();
        holder.tvAuthorReview.setText(authorLabel);
        holder.tvContentReview.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return reviewList == null ? 0 : reviewList.size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {

        TextView tvContentReview;
        TextView tvAuthorReview;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            tvContentReview = itemView.findViewById(R.id.tv_detail_content_review);
            tvAuthorReview = itemView.findViewById(R.id.tv_detail_author_review);
        }
    }

}
