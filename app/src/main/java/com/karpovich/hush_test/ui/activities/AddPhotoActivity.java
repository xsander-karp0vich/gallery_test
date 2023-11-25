package com.karpovich.hush_test.ui.activities;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.karpovich.hush_test.R;
import com.karpovich.hush_test.data.entities.Photo;
import com.karpovich.hush_test.databinding.ActivityAddPhotoBinding;
import com.karpovich.hush_test.ui.viewmodels.AddPhotoViewModel;

import java.net.URI;

public class AddPhotoActivity extends AppCompatActivity {

    private ActivityAddPhotoBinding binding;
    private AddPhotoViewModel viewModel;
    private static final int REQUEST_CODE = 123;
    private Uri image = null;
    private String title = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPhotoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        checkPermissions();
        setClickListeners();
        clickListenerSavePhoto();
    }

    private void init(){
        viewModel = new ViewModelProvider(this).get(AddPhotoViewModel.class);
        binding.photoTitleEditText.setVisibility(View.GONE);
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(AddPhotoActivity.this, READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            imagePicker();
        }
        else if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE
            );
        }
        else {
            Toast.makeText(this,"Check permissions",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE){
            imagePicker();
        }
    }

    private void imagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null){
            image = data.getData();
            binding.photoImageView.setImageURI(image);
            binding.photoTitleEditText.setVisibility(View.VISIBLE);
        }
    }

    private String formatEditText(){
        return binding.photoTitleEditText.getText().toString().trim();
    }
    private void clickListenerSavePhoto(){
        binding.savePhotoImageButton.setOnClickListener(view -> {
            title = formatEditText();
            if (image != null && !title.isEmpty()){
                Photo photo = new Photo(0,title,image.toString());
                viewModel.insertPhoto(photo);
                finish();
            } else {
                Toast.makeText(this, "Something went wrong...",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setClickListeners(){
        binding.mainActivityImageButton.setOnClickListener(view -> {
            finish();
        });
    }
}
