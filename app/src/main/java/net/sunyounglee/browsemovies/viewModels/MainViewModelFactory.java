package net.sunyounglee.browsemovies.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import net.sunyounglee.browsemovies.repositories.MainViewRepository;

public class MainViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    private final Application application;
    private MainViewRepository mMainViewRepository;

    public MainViewModelFactory(@NonNull Application application, MainViewRepository mainViewRepository) {
        this.application = application;
        this.mMainViewRepository = mainViewRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == MainViewModel.class) {
            return (T) new MainViewModel(application, mMainViewRepository);
        }
        return null;
    }
}
