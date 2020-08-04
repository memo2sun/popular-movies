package net.sunyounglee.browsemovies.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import net.sunyounglee.browsemovies.repositories.DetailViewRepository;

public class DetailViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    private final Application application;
    private final long movieId;
    DetailViewRepository detailViewRepository;

    public DetailViewModelFactory(long movieId, @NonNull Application application, DetailViewRepository detailViewRepository) {
        this.application = application;
        this.movieId = movieId;
        this.detailViewRepository = detailViewRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == DetailViewModel.class) {
            return (T) new DetailViewModel(movieId, application, detailViewRepository);
        }
        return null;
    }
}
