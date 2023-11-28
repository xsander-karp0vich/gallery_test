package com.karpovich.hush_test.ui.activities;

import static android.Manifest.permission.MANAGE_EXTERNAL_STORAGE;
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
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
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

    private static final int REQUEST_CODE = 321;
    private static final int REQUEST_CODE_M = 100;
    private static final int REQUEST_CODE_R = 101;
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
    }

    private void init(){
        viewModel = new ViewModelProvider(this).get(AddPhotoViewModel.class);
        binding.photoTitleEditText.setVisibility(View.GONE);
    }

    private void checkPermissions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            if (!Environment.isExternalStorageManager()){
                try {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setData(Uri.parse(String.format("package:%s",getApplicationContext().getPackageName())));
                    startActivityIfNeeded(intent,REQUEST_CODE_R);
                } catch (Exception e){
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            if (ContextCompat.checkSelfPermission(getApplicationContext(),
                    READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(AddPhotoActivity.this,
                        new String[]{READ_EXTERNAL_STORAGE},REQUEST_CODE_M);
            } else {
                galleryIntent();
            }
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_CODE_M:
            case REQUEST_CODE_R:
                galleryIntent();
            break;

        }
    }

    private void galleryIntent() {
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

    private void setClickListeners(){
        binding.savePhotoImageButton.setOnClickListener(view -> {
            title = formatEditText();
            if (image != null && !title.isEmpty()){
                Photo photo = new Photo(0,title,image.toString());
                viewModel.insertPhoto(photo);
                Toast.makeText(this,R.string.saved_success,Toast.LENGTH_SHORT).show();
                finish();
            } else {
                //Toast.makeText(this, R.string.error_photo,Toast.LENGTH_SHORT).show();
            }
        });

        binding.mainActivityImageButton.setOnClickListener(view -> {
            finish();
        });
    }
}
