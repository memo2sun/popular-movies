package net.sunyounglee.browsemovies.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.squareup.picasso.Picasso;

import net.sunyounglee.browsemovies.AppExecutors;
import net.sunyounglee.browsemovies.R;
import net.sunyounglee.browsemovies.data.AppDatabase;
import net.sunyounglee.browsemovies.data.MovieDao;
import net.sunyounglee.browsemovies.data.ReviewDao;
import net.sunyounglee.browsemovies.data.TrailerDao;
import net.sunyounglee.browsemovies.databinding.ActivityDetailBinding;
import net.sunyounglee.browsemovies.models.Movie;
import net.sunyounglee.browsemovies.models.Review;
import net.sunyounglee.browsemovies.models.Trailer;
import net.sunyounglee.browsemovies.repositories.DetailViewRepository;
import net.sunyounglee.browsemovies.viewModels.DetailViewModel;
import net.sunyounglee.browsemovies.viewModels.DetailViewModelFactory;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();
    public static final String YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v=";
    private static final String BACKDROP_IMG_URL = "http://image.tmdb.org/t/p/w500/";

    private String[] tabTitle;

    private static final String MOVIE_INTENT_EXTRA = "MOVIE_DATA";

    private static AppDatabase mDb;

    private TextView tvTitle;
    private ImageView imgBackDrop, imgPlay, favoriteOnOff;

    private Movie movie;

    private boolean mFavorite;
    private long mMovieId;

    private DetailViewModel detailViewModel;
    private String mYoutubePath;
    private ActivityDetailBinding mBinding;

    private List<Trailer> trailerList;
    private List<Review> reviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_detail);
        //   mBinding.setLifecycleOwner(this);

        viewPagerSetup();

        initViews();
        mDb = AppDatabase.getInstance(this);

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(MOVIE_INTENT_EXTRA)) {
            movie = intent.getParcelableExtra(MOVIE_INTENT_EXTRA);

        }
        assert movie != null;
        mMovieId = movie.getMovieId();
        MovieDao movieDao = mDb.movieDao();

        ReviewDao reviewDao = mDb.reviewDao();
        TrailerDao trailerDao = mDb.trailerDao();
        DetailViewRepository detailViewRepository = DetailViewRepository.getInstance(movieDao, reviewDao, trailerDao, mMovieId);
        DetailViewModelFactory detailViewModelFactory = new DetailViewModelFactory(mMovieId, getApplication(), detailViewRepository);
        detailViewModel = new ViewModelProvider(this, detailViewModelFactory).get(DetailViewModel.class);

        detailViewModel.getMovies().observe(this, movie -> {
            if (movie != null) {
                populateMovieUI(movie);
                mFavorite = movie.getIsFavorite();
                Log.d(TAG, " favorite value: " + mFavorite);
                if (mFavorite) {
                    favoriteOnOff.setImageResource(R.drawable.ic_baseline_favorite_24);
                } else {
                    favoriteOnOff.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                }
                mMovieId = movie.getMovieId();
            }
        });

        detailViewModel.getTrailers().observe(this, trailerPageObject -> {
            trailerList = trailerPageObject.getTrailerResultList();
            if (trailerList != null && trailerList.size() != 0) {
                mYoutubePath = YOUTUBE_BASE_URL + trailerList.get(0).getTrailerKey();
                imgPlay.setOnClickListener(v -> {

                    Intent intent1 = new Intent(Intent.ACTION_VIEW);
                    intent1.setData(Uri.parse(mYoutubePath));
                    if (intent1.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent1);
                    }
                });
            } else {
                imgPlay.setVisibility(View.INVISIBLE);
            }
        });

        detailViewModel.getReviews().observe(this, reviewPageObject -> reviewList = reviewPageObject.getReviewResultList());
    }
    //  }

    private void viewPagerSetup() {
        tabTitle = new String[]{"SYNOPSIS", "TRAILERS", "REVIEWS"};
        MovieDetailViewPagerAdapter movieDetailViewPagerAdapter = new MovieDetailViewPagerAdapter(this);
        ViewPager2 viewPager = mBinding.pager;
        viewPager.setAdapter(movieDetailViewPagerAdapter);
        TabLayout tabLayout = mBinding.tabLayout;
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabTitle[position])
        ).attach();
    }

    private void initViews() {

        tvTitle = mBinding.tvTitle;

        imgBackDrop = mBinding.ivBackdrop;
        imgPlay = mBinding.ivPlay;
        favoriteOnOff = mBinding.ivFavorite;

        favoriteOnOff.setOnClickListener(v -> AppExecutors.getInstance().diskIO().execute(() -> {
            if (mFavorite) {
                detailViewModel.deleteMovie(mMovieId);
                mDb.reviewDao().deleteReviewById(mMovieId);
                mDb.trailerDao().deleteTrailerById(mMovieId);

                runOnUiThread(() -> favoriteOnOff.setImageResource(R.drawable.ic_baseline_favorite_border_24));
                finish();
            } else {
                detailViewModel.addToFavorite(mMovieId);
                for (Trailer trailer : trailerList) {
                    trailer.setTrailer_movieId(mMovieId);
                    mDb.trailerDao().insertTrailer(trailer);
                }
                for (Review review : reviewList) {
                    review.setReview_movieId(mMovieId);
                    mDb.reviewDao().insertReview(review);
                }

                runOnUiThread(() -> favoriteOnOff.setImageResource(R.drawable.ic_baseline_favorite_24));
            }
        }));
    }

    private void populateMovieUI(Movie movieEntry) {
        if (movieEntry == null) return;

        tvTitle.setText(movieEntry.getTitle());

        String backDropPath = BACKDROP_IMG_URL + movieEntry.getBackDrop_image();
        Picasso.get()
                .load(backDropPath)
                .placeholder(R.drawable.image_default)
                .error(R.drawable.image_error)
                .fit().centerCrop()
                .into(imgBackDrop);
        imgPlay.setImageResource(R.drawable.ic_baseline_play_circle_outline_24);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {
            String youtubeURLToShare = mYoutubePath;
            shareText(youtubeURLToShare);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void shareText(String youtubeURLToShare) {
        String mimeType = "text/plain";
        String title = "Share Trailer";
        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle(title)
                .setText(youtubeURLToShare)
                .startChooser();
    }
}