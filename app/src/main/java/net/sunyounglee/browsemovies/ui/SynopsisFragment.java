package net.sunyounglee.browsemovies.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.squareup.picasso.Picasso;

import net.sunyounglee.browsemovies.R;
import net.sunyounglee.browsemovies.data.AppDatabase;
import net.sunyounglee.browsemovies.data.MovieDao;
import net.sunyounglee.browsemovies.data.ReviewDao;
import net.sunyounglee.browsemovies.data.TrailerDao;
import net.sunyounglee.browsemovies.databinding.FragmentSynopsisBinding;
import net.sunyounglee.browsemovies.models.Movie;
import net.sunyounglee.browsemovies.repositories.DetailViewRepository;
import net.sunyounglee.browsemovies.viewModels.DetailViewModel;
import net.sunyounglee.browsemovies.viewModels.DetailViewModelFactory;


public class SynopsisFragment extends Fragment {
    private static final String MOVIE_INTENT_EXTRA = "MOVIE_DATA";
    private static final String IMG_URL = "http://image.tmdb.org/t/p/w185/";

    private TextView tvRating, tvOverview, tvReleaseDate;
    private ImageView imgThumbnail;
    private DetailViewModel detailViewModel;

    private FragmentSynopsisBinding mBinding;
    private Movie movieFromIntent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(MOVIE_INTENT_EXTRA)) {
            movieFromIntent = getActivity().getIntent().getParcelableExtra(MOVIE_INTENT_EXTRA);
            long movieId = movieFromIntent.getMovieId();
        }
        AppDatabase mDb = AppDatabase.getInstance(this.getActivity());
        MovieDao movieDao = mDb.movieDao();
        ReviewDao reviewDao = mDb.reviewDao();
        TrailerDao trailerDao = mDb.trailerDao();
        DetailViewRepository detailViewRepository = DetailViewRepository.getInstance(movieDao, reviewDao, trailerDao, movieFromIntent);
        DetailViewModelFactory detailViewModelFactory = new DetailViewModelFactory(movieFromIntent, getActivity().getApplication(), detailViewRepository);
        detailViewModel = new ViewModelProvider(this, detailViewModelFactory).get(DetailViewModel.class);

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_synopsis, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        tvRating = mBinding.tvRating;
        tvReleaseDate = mBinding.tvReleaseDate;
        tvOverview = mBinding.tvOverview;
        imgThumbnail = mBinding.ivMovieThumbnail;

        detailViewModel.getMovieFromDB().observe(getActivity(), movie -> {
            if (movie != null) {
                populateUI(movie);
            } else {
                populateUI(movieFromIntent);
            }
        });
    }

    private void populateUI(Movie movie) {
        tvOverview.setText(movie.getOverview());
        tvReleaseDate.setText(movie.getRelease_date());
        tvRating.setText(movie.getRating() + "/10");
        String thumbnailPath = IMG_URL + movie.getPoster_image();

        Picasso.get()
                .load(thumbnailPath)
                .placeholder(R.drawable.image_default)
                .error(R.drawable.image_error)
                .into(imgThumbnail);

    }
}