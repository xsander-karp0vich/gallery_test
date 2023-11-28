package com.karpovich.hush_test.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.karpovich.hush_test.R;
import com.karpovich.hush_test.data.entities.Photo;
import com.karpovich.hush_test.databinding.ActivityPhotoDetailBinding;
import com.karpovich.hush_test.ui.viewmodels.PhotoDetailViewModel;

import java.io.Serializable;

public class PhotoDetailActivity extends AppCompatActivity {

    private ActivityPhotoDetailBinding binding;
    private PhotoDetailViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhotoDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        setClickListeners();
        updateUi();
        observeViewModel();
    }

    private void init() {
        viewModel = new ViewModelProvider(this).get(PhotoDetailViewModel.class);
    }

    private void setClickListeners() {

        binding.deleteImageButton.setOnClickListener(view -> {
            Intent intent = getIntent();
            int id = intent.getIntExtra("photoId",-1);
            if (id!=-1){
                viewModel.deletePhoto(id);
                finish();
            }
        });

        binding.mainActivityImageButton.setOnClickListener(view -> {
            finish();
        });
    }

    private void updateUi() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        binding.textView.setText(title);
    }

    private void observeViewModel() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("photoId",-1);
        if (id!=-1){
            viewModel.getPhoto(id).observe(this, new Observer<Photo>() {
                @Override
                public void onChanged(Photo photo) {
                    if (photo!=null){
                        binding.imageView.setImageURI(Uri.parse(photo.getUri()));
                    }
                }
            });
        }
    }

    public static Intent newIntent(Context context, String title, String uri, int photoId) {
        Intent intent = new Intent(context,PhotoDetailActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("uri",uri);
        intent.putExtra("photoId",photoId);
        return intent;
    }
}