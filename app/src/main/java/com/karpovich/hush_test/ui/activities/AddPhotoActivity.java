package com.karpovich.hush_test.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.karpovich.hush_test.R;
import com.karpovich.hush_test.databinding.ActivityAddPhotoBinding;

public class AddPhotoActivity extends AppCompatActivity {

    private ActivityAddPhotoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPhotoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}