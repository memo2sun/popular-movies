package net.sunyounglee.browsemovies.ui;

import android.content.Intent;
import android.net.Uri;
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
import net.sunyounglee.browsemovies.databinding.FragmentTrailerBinding;
import net.sunyounglee.browsemovies.models.Movie;
import net.sunyounglee.browsemovies.models.Trailer;
import net.sunyounglee.browsemovies.repositories.DetailViewRepository;
import net.sunyounglee.browsemovies.viewModels.DetailViewModel;
import net.sunyounglee.browsemovies.viewModels.DetailViewModelFactory;

import java.util.List;

public class TrailerFragment extends Fragment implements TrailerRecyclerViewAdapter.TrailerAdapterOnClickHandler {
    private static final String TAG = TrailerFragment.class.getSimpleName();
    private static final String MOVIE_INTENT_EXTRA = "MOVIE_DATA";

    private RecyclerView mRecyclerView;
    private TextView mNoTrailerMessage;
    private TrailerRecyclerViewAdapter mTrailerRecyclerViewAdapter;

    private DetailViewModel detailViewModel;
    private Movie movie;

    private FragmentTrailerBinding mBinding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(MOVIE_INTENT_EXTRA)) {
            movie = intent.getParcelableExtra(MOVIE_INTENT_EXTRA);
        }
        long movieId = movie.getMovieId();
        AppDatabase mDb = AppDatabase.getInstance(this.getActivity());
        MovieDao movieDao = mDb.movieDao();
        ReviewDao reviewDao = mDb.reviewDao();
        TrailerDao trailerDao = mDb.trailerDao();
        DetailViewRepository detailViewRepository = DetailViewRepository.getInstance(movieDao, reviewDao, trailerDao, movieId);
        DetailViewModelFactory detailViewModelFactory = new DetailViewModelFactory(movieId, getActivity().getApplication(), detailViewRepository);
        detailViewModel = new ViewModelProvider(this, detailViewModelFactory).get(DetailViewModel.class);

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_trailer, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView = mBinding.rvTrailerList;
        mNoTrailerMessage = mBinding.tvNoTrailerMessage;

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.setHasFixedSize(true);

        mTrailerRecyclerViewAdapter = new TrailerRecyclerViewAdapter(this);
        mRecyclerView.setAdapter(mTrailerRecyclerViewAdapter);

        setupViewModel();

    }

    private void setupViewModel() {
        detailViewModel.getTrailerFromDB().observe(this, trailers -> {
            if (trailers == null || trailers.size() == 0) {
                loadTrailerFromServer();
            } else {
                Log.d(TAG, "Trailer Size from DB: " + trailers.size());
                mTrailerRecyclerViewAdapter.setTrailerData(trailers);
            }
        });
    }

    private void loadTrailerFromServer() {
        detailViewModel.getTrailers().observe(this, trailerPageObject -> {
            List<Trailer> trailers = trailerPageObject.getTrailerResultList();
            if (trailers == null || trailers.size() == 0) {
                mRecyclerView.setVisibility(View.INVISIBLE);
                mNoTrailerMessage.setVisibility(View.VISIBLE);
            } else {
                Log.d(TAG, "Trailer Size : " + trailers.size());
                mTrailerRecyclerViewAdapter.setTrailerData(trailers);
            }
        });

    }

    @Override
    public void onClick(String youtubeURL) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(youtubeURL));
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            getContext().startActivity(intent);
        }
    }
}