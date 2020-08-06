package net.sunyounglee.browsemovies.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.sunyounglee.browsemovies.R;
import net.sunyounglee.browsemovies.data.AppDatabase;
import net.sunyounglee.browsemovies.data.MovieDao;
import net.sunyounglee.browsemovies.data.ReviewDao;
import net.sunyounglee.browsemovies.data.TrailerDao;
import net.sunyounglee.browsemovies.databinding.FragmentReviewBinding;
import net.sunyounglee.browsemovies.models.Movie;
import net.sunyounglee.browsemovies.repositories.DetailViewRepository;
import net.sunyounglee.browsemovies.viewModels.DetailViewModel;
import net.sunyounglee.browsemovies.viewModels.DetailViewModelFactory;

public class ReviewFragment extends Fragment {
    private static final String TAG = ReviewFragment.class.getSimpleName();
    private static final String MOVIE_INTENT_EXTRA = "MOVIE_DATA";
    private DetailViewModel detailViewModel;

    private RecyclerView mRecyclerView;
    private ReviewRecyclerViewAdapter mReviewRecyclerViewAdapter;

    private FragmentReviewBinding mBinding;
    private TextView mNoReviewMessage;

    private Movie movie;
    private long movieId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(MOVIE_INTENT_EXTRA)) {
            movie = intent.getParcelableExtra(MOVIE_INTENT_EXTRA);
        }
        assert movie != null;
        movieId = movie.getMovieId();
        AppDatabase mDb = AppDatabase.getInstance(this.getActivity());
        MovieDao movieDao = mDb.movieDao();
        ReviewDao reviewDao = mDb.reviewDao();
        TrailerDao trailerDao = mDb.trailerDao();
        DetailViewRepository detailViewRepository = DetailViewRepository.getInstance(movieDao, reviewDao, trailerDao, movie);
        DetailViewModelFactory detailViewModelFactory = new DetailViewModelFactory(movie, getActivity().getApplication(), detailViewRepository);
        detailViewModel = new ViewModelProvider(this, detailViewModelFactory).get(DetailViewModel.class);

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_review, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView = mBinding.rvReviewList;
        mNoReviewMessage = mBinding.tvNoReviewMessage;

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.setHasFixedSize(true);

        mReviewRecyclerViewAdapter = new ReviewRecyclerViewAdapter();
        mRecyclerView.setAdapter(mReviewRecyclerViewAdapter);

        initView();

        setupViewModel();

    }

    private void initView() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mNoReviewMessage.setVisibility(View.VISIBLE);
    }

    private void showReviewDataView() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mNoReviewMessage.setVisibility(View.INVISIBLE);
    }

    private void setupViewModel() {

        detailViewModel.getReviewsFromDB(movieId).observe(this, reviewList -> {
            if (reviewList == null || reviewList.size() == 0) {
                loadReviewFromServer();
            } else {
                Log.d(TAG, "Review Size from db : " + reviewList.size());
                mReviewRecyclerViewAdapter.setReviewData(reviewList);
                showReviewDataView();
            }
        });
    }

    private void loadReviewFromServer() {
        detailViewModel.getReviewFromServer().observe(this, reviewList -> {
            if (reviewList == null || reviewList.size() == 0) {
                initView();
            } else {
                Log.d(TAG, "Review Size from server : " + reviewList.size());
                mReviewRecyclerViewAdapter.setReviewData(reviewList);
                showReviewDataView();
            }
        });
    }
}