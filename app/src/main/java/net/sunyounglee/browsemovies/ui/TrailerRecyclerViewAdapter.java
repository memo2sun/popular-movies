package net.sunyounglee.browsemovies.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import net.sunyounglee.browsemovies.R;
import net.sunyounglee.browsemovies.databinding.TrailerListItemBinding;
import net.sunyounglee.browsemovies.models.Trailer;

import java.util.List;

public class TrailerRecyclerViewAdapter extends RecyclerView.Adapter<TrailerRecyclerViewAdapter.TrailerAdapterViewHolder> {
    private static String TAG = TrailerRecyclerViewAdapter.class.getSimpleName();

    public static final String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";

    public static final String YOUTUBE_THUMBNAIL_BASE_URL = "https://img.youtube.com/vi/";
    public static final String YOUTUBE_THUMBNAIL_URL_JPG = "/0.jpg";

    private List<Trailer> mTrailerDataList;
    private final TrailerAdapterOnClickHandler mClickHandler;

    private ImageView mImgPlay;

    /**
     * The interface that receives onClick messages.
     */
    public interface TrailerAdapterOnClickHandler {
        void onClick(String youtubeURL);
    }

    /**
     * Creates a TrailerRecyclerViewAdapter.
     *
     * @param clickHandler The on-click handler for this adapter. This single handler is called
     *                     when an item is clicked.
     */
    public TrailerRecyclerViewAdapter(TrailerAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @NonNull
    @Override
    public TrailerRecyclerViewAdapter.TrailerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        net.sunyounglee.browsemovies.databinding.TrailerListItemBinding itemBinding = TrailerListItemBinding.inflate(inflater, parent, false);
        return new TrailerRecyclerViewAdapter.TrailerAdapterViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerRecyclerViewAdapter.TrailerAdapterViewHolder holder, int position) {
        Trailer trailer = mTrailerDataList.get(position);

        String mTrailerThumbnailPath = YOUTUBE_THUMBNAIL_BASE_URL + trailer.getTrailerKey() +
                YOUTUBE_THUMBNAIL_URL_JPG;
        Picasso.get()
                .load(mTrailerThumbnailPath)
                .placeholder(R.drawable.image_default)
                .error(R.drawable.image_error)
                .into(holder.mTrailerThumbnail);

        mImgPlay.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);

        String mTrailerTitle = trailer.getTrailerName();
        holder.mTrailerName.setText(mTrailerTitle);
        Log.d(TAG, "Trailer Title: " + mTrailerTitle);
    }

    @Override
    public int getItemCount() {
        return mTrailerDataList == null ? 0 : mTrailerDataList.size();
    }

    public class TrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView mTrailerThumbnail;
        public final TextView mTrailerName;

        public TrailerAdapterViewHolder(TrailerListItemBinding itemBinding) {
            super(itemBinding.getRoot());
            mTrailerThumbnail = itemBinding.ivTrailerThumbnail;
            mTrailerName = itemBinding.tvTrailerName;
            mImgPlay = itemBinding.ivPlay;
            mImgPlay.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String youtubeURL = YOUTUBE_BASE_URL + mTrailerDataList.get(adapterPosition).getTrailerKey();
            mClickHandler.onClick(youtubeURL);
        }
    }

    public void setTrailerData(List<Trailer> trailerData) {
        mTrailerDataList = trailerData;
        notifyDataSetChanged();

    }
}
