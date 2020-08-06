package net.sunyounglee.browsemovies.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import net.sunyounglee.browsemovies.models.Movie;
import net.sunyounglee.browsemovies.repositories.DetailViewRepository;

public class DetailViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    private final Application application;
    private final Movie movie;
    DetailViewRepository detailViewRepository;

    public DetailViewModelFactory(Movie movie, @NonNull Application application, DetailViewRepository detailViewRepository) {
        this.application = application;
        this.movie = movie;
        this.detailViewRepository = detailViewRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == DetailViewModel.class) {
            return (T) new DetailViewModel(movie, application, detailViewRepository);
        }
        return null;
    }
}
