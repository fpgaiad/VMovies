package br.com.fpgaiad.vmovies.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.fpgaiad.vmovies.R;
import br.com.fpgaiad.vmovies.entities.Constants;
import br.com.fpgaiad.vmovies.entities.Movie;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    private final Context context;
    private final List<Movie> movieList;
    private final ClickListener mOnclickListener;

    public ListAdapter(Context context, List<Movie> movieList, ClickListener listener) {

        this.context = context;
        this.movieList = movieList;
        mOnclickListener = listener;
    }

    public interface ClickListener {

        void onListItemClick(int clickedItemIndex);
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.list_item, parent, false);

        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {

        Movie movie = movieList.get(position);

        String imageUrl = Constants.IMAGE_BASE_URL + movie.getPosterPath();
        Picasso.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(holder.imgThumb);
    }

    @Override
    public int getItemCount() {
        return movieList == null? 0 : movieList.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ImageView imgThumb;

        public ListViewHolder(View itemView) {
            super(itemView);

            imgThumb = itemView.findViewById(R.id.iv_thumb);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOnclickListener.onListItemClick(getAdapterPosition());
        }
    }

}
