package net.sunyounglee.browsemovies.ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MovieDetailViewPagerAdapter extends FragmentStateAdapter {

    private static final int SYNOPSIS = 0;
    private static final int TRAILER = 1;
    private static final int REVIEW = 2;

    private static final int TAB_COUNT = 3;

    public MovieDetailViewPagerAdapter(FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case SYNOPSIS:
                return new SynopsisFragment();
            case TRAILER:
                return new TrailerFragment();
            case REVIEW:
                return new ReviewFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return TAB_COUNT;
    }

}
