package com.karpovich.hush_test.data.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.karpovich.hush_test.data.entities.Photo;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface PhotoDao {
    @Query("SELECT * FROM photos" )
    LiveData<List<Photo>> getAllPhotos();

    @Insert
    Completable insertPhoto (Photo photo);

    @Query("DELETE FROM photos WHERE id = :photoId")
    Completable removePhoto(int photoId);

    @Query("SELECT * FROM photos WHERE id = :photoId")
    LiveData<Photo> getPhotoById(int photoId);

    @Update
    Completable updatePhoto(Photo photo);
}
