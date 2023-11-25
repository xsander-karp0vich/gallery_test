package com.karpovich.hush_test.ui.viewmodels;

import android.app.Application;
import android.content.Intent;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.karpovich.hush_test.data.db.PhotoDao;
import com.karpovich.hush_test.data.db.PhotoDataBase;
import com.karpovich.hush_test.data.entities.Photo;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class MainActivityViewModel extends AndroidViewModel {

    private final PhotoDao photoDao;
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        photoDao = PhotoDataBase.getInstance(application).photoDao();
    }
    public LiveData<List<Photo>> getAllPhotos() {
        return photoDao.getAllPhotos();
    }
}
