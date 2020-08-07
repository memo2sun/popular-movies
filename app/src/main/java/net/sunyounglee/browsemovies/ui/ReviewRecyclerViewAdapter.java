package net.sunyounglee.browsemovies.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.sunyounglee.browsemovies.databinding.ReviewListItemBinding;
import net.sunyounglee.browsemovies.models.Review;

import java.util.List;

public class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<ReviewRecyclerViewAdapter.ReviewAdapterViewHolder> {

    private List<Review> mReviewDataList;

    @NonNull
    @Override
    public ReviewRecyclerViewAdapter.ReviewAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        ReviewListItemBinding itemBinding = ReviewListItemBinding.inflate(inflater, parent, false);
        return new ReviewAdapterViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewRecyclerViewAdapter.ReviewAdapterViewHolder holder, int position) {
        Review review = mReviewDataList.get(position);
        String mReviewAuthor = review.getAuthor();
        String mReviewContent = review.getContent();

        holder.mAuthor.setText(mReviewAuthor);
        holder.mContent.setText(mReviewContent);
    }

    @Override
    public int getItemCount() {
        return mReviewDataList == null ? 0 : mReviewDataList.size();
    }

    public static class ReviewAdapterViewHolder extends RecyclerView.ViewHolder {
        public final TextView mAuthor;
        public final TextView mContent;

        public ReviewAdapterViewHolder(ReviewListItemBinding itemBinding) {
            super(itemBinding.getRoot());
            mAuthor = itemBinding.tvAuthor;
            mContent = itemBinding.tvContent;
        }
    }

    public void setReviewData(List<Review> reviewData) {
        mReviewDataList = reviewData;
        notifyDataSetChanged();

    }
}
