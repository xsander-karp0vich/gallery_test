package com.karpovich.hush_test.data.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "photos")
public class Photo {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String uri;
    public Photo(int id, String title, String uri) {
        this.id = id;
        this.title = title;
        this.uri = uri;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}