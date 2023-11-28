package com.karpovich.hush_test.ui.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.karpovich.hush_test.data.db.PhotoDao;
import com.karpovich.hush_test.data.db.PhotoDataBase;
import com.karpovich.hush_test.data.entities.Photo;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PhotoDetailViewModel extends AndroidViewModel {

    private final PhotoDao photoDao;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    public PhotoDetailViewModel(@NonNull Application application) {
        super(application);
        photoDao = PhotoDataBase.getInstance(application).photoDao();
    }

    public void deletePhoto(int photoId) {
        photoDao.removePhoto(photoId)
                .subscribeOn(Schedulers.newThread())
                .subscribe();
    }

    public LiveData<Photo> getPhotoById(int photoId){
        return photoDao.getPhotoById(photoId);
    }

    public void updatePhoto(Photo photo) {
        photoDao.updatePhoto(photo)
                .subscribeOn(Schedulers.newThread())
                .subscribe();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
