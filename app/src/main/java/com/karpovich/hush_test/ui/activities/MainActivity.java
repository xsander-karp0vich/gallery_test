package com.karpovich.hush_test.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.karpovich.hush_test.data.entities.Photo;
import com.karpovich.hush_test.databinding.ActivityMainBinding;
import com.karpovich.hush_test.ui.adapters.PhotoAdapter;
import com.karpovich.hush_test.ui.viewmodels.MainActivityViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;
    PhotoAdapter photoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        setupRecycleView();
        observeViewModel();
    }
    private void init() {
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        setClickListeners();
    }

    private void setClickListeners() {
        binding.addPhotoButton.setOnClickListener(view -> {
           Intent intent = new Intent(MainActivity.this, AddPhotoActivity.class);
           startActivity(intent);
        });
    }

    private void observeViewModel() {
        viewModel.getAllPhotos().observe(this, new Observer<List<Photo>>() {
            @Override
            public void onChanged(List<Photo> photos) {
                photoAdapter.setPhotos(photos);
            }
        });
    }

    private void setupRecycleView() {
        photoAdapter = new PhotoAdapter();
        binding.photoRecycleView.setAdapter(photoAdapter);
        binding.photoRecycleView.setLayoutManager(new GridLayoutManager(this,4));
        photoAdapter.setOnPhotoClickListener(new PhotoAdapter.OnPhotoClickListener() {
            @Override
            public void onClick(Photo photo) {
                int i = 0;
                Log.d("ТАГ", "onClick: "+i);
                i++;
            }
        });
    }
}