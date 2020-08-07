package net.sunyounglee.browsemovies.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import net.sunyounglee.browsemovies.R;
import net.sunyounglee.browsemovies.databinding.MovieListItemBinding;
import net.sunyounglee.browsemovies.models.Movie;

import java.util.List;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.MovieAdapterViewHolder> {

    private static final String TAG = MovieRecyclerViewAdapter.class.getSimpleName();
    private List<Movie> mMovieDataList;

    private static final String IMG_URL = "http://image.tmdb.org/t/p/w185/";
    private final MovieAdapterOnClickHandler mClickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    /**
     * Creates a MovieRecyclerViewAdapter.
     *
     * @param clickHandler The on-click handler for this adapter. This single handler is called
     *                     when an item is clicked.
     */
    public MovieRecyclerViewAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(context);
        MovieListItemBinding itemBinding = MovieListItemBinding.inflate(inflater, parent, false);
        return new MovieAdapterViewHolder(itemBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder holder, int position) {
        Movie movie = mMovieDataList.get(position);
        String mMovieImagePath = movie.getPoster_image();
        String thumbnailPath = IMG_URL + mMovieImagePath;

        Picasso.get()
                .load(thumbnailPath)
                // .placeholder(R.drawable.image_default)
                .error(R.drawable.image_error)
                .fit().centerCrop()
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mMovieDataList == null ? 0 : mMovieDataList.size();
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mImageView;

        public MovieAdapterViewHolder(MovieListItemBinding itemBinding) {
            super(itemBinding.getRoot());
            mImageView = itemBinding.ivMovieCover;
            mImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie movie = mMovieDataList.get(adapterPosition);
            mClickHandler.onClick(movie);
        }
    }

    public void setMovieData(List<Movie> movieData) {
        mMovieDataList = movieData;
        notifyDataSetChanged();
    }
}



