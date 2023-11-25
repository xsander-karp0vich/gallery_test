package com.karpovich.hush_test.data.db;

import android.net.Uri;

import com.karpovich.hush_test.data.entities.Photo;

import junit.framework.TestCase;

import org.junit.Test;

public class PhotoDataBaseTest extends TestCase {
    public class PhotoTest {
        @Test
        public void testPhoto() {
            Photo photo = new Photo(1, "title", Uri.parse("content://path/to/resource"));

            assertEquals(1, photo.getId());
            assertEquals("title", photo.getTitle());
            assertEquals(Uri.parse("content://path/to/resource"), photo.getUri());

            photo.setId(2);
            photo.setTitle("new title");
            photo.setUri(Uri.parse("content://path/to/new/resource"));

            assertEquals(2, photo.getId());
            assertEquals("new title", photo.getTitle());
            assertEquals(Uri.parse("content://path/to/new/resource"), photo.getUri());
        }
    }

}