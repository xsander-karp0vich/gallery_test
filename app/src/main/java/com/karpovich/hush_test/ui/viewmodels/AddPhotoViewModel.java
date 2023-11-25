package com.karpovich.hush_test.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.karpovich.hush_test.data.db.PhotoDao;
import com.karpovich.hush_test.data.db.PhotoDataBase;
import com.karpovich.hush_test.data.entities.Photo;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddPhotoViewModel extends AndroidViewModel {

    private final PhotoDao photoDao;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    public AddPhotoViewModel(@NonNull Application application) {
        super(application);
        photoDao = PhotoDataBase.getInstance(application).photoDao();
    }

    public void insertPhoto(Photo photo) {
        Disposable disposable = photoDao.insertPhoto(photo)
                .subscribeOn(Schedulers.newThread())
                .subscribe();
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
