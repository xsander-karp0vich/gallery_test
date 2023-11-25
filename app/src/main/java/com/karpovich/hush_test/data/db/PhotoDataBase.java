package com.karpovich.hush_test.data.db;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.karpovich.hush_test.data.converters.Converters;
import com.karpovich.hush_test.data.entities.Photo;

@Database(entities = {Photo.class},version = 1,exportSchema = false)
@TypeConverters({Converters.class})
public abstract class PhotoDataBase extends RoomDatabase {

    private static final String DB_NAME = "photo.db";
    private static PhotoDataBase instance = null;

    public static PhotoDataBase getInstance(Application application){
        if (instance == null){
            instance = Room.databaseBuilder(
                    application,
                    PhotoDataBase.class,
                    DB_NAME
            ).build();
        }
        return instance;
    }
    public abstract PhotoDao photoDao();
}
