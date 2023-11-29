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
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.disklrucache.DiskLruCache;
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
        updatePhotoTitle();
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
                Toast.makeText(this,"Успешно удаленно", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        binding.mainActivityImageButton.setOnClickListener(view -> {
            finish();
        });
    }

    private void updatePhotoTitle() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("photoId", -1);
        Log.d("ТАГТАГ", "updatePhotoTitle: " + id);

        viewModel.getPhotoById(id).observe(this, new Observer<Photo>() {
            @Override
            public void onChanged(Photo photo) {
                handlePhotoChange(photo);
            }
        });
    }

    private void handlePhotoChange(Photo photo) {
        if (photo != null) {
            setEditorActionListener(photo);
        }
    }

    private void setEditorActionListener(Photo photo) {
        binding.editTextTitle.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    handleDoneAction(photo);
                    Toast.makeText(PhotoDetailActivity.this, R.string.saved_success,Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
        binding.editTextDescription.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    handleDoneAction(photo);
                    Toast.makeText(PhotoDetailActivity.this, R.string.saved_success,Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
        binding.savePhotoImageButton.setOnClickListener(view -> {
            Toast.makeText(PhotoDetailActivity.this, R.string.saved_success,Toast.LENGTH_SHORT).show();
            handleDoneAction(photo);
        });
    }

    private String formatEditTextTitle() {
        return binding.editTextTitle.getText().toString().trim();
    }
    private String formatEditTextDescription() {
        return binding.editTextDescription.getText().toString().trim();
    }
    private void handleDoneAction(Photo photo) {
        String newTitle = formatEditTextTitle();
        String newDescription = formatEditTextDescription();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.editTextTitle.getWindowToken(), 0);
        if (!newTitle.isEmpty()) {
            photo.setTitle(newTitle);
            viewModel.updatePhoto(photo);
            binding.editTextTitle.clearFocus();
        }
        if (!newDescription.isEmpty()) {
            photo.setDescription(newDescription);
            viewModel.updatePhoto(photo);
            binding.editTextTitle.clearFocus();
        }
    }


    private void updateUi() {
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        binding.editTextDescription.setText(description);
        binding.editTextTitle.setText(title);
    }

    private void observeViewModel() {
        Intent intent = getIntent();
        int id = intent.getIntExtra("photoId",-1);
        if (id!=-1){
            viewModel.getPhotoById(id).observe(this, new Observer<Photo>() {
                @Override
                public void onChanged(Photo photo) {
                    if (photo!=null){
                        binding.imageView.setImageURI(Uri.parse(photo.getUri()));
                    }
                }
            });
        }
    }

    public static Intent newIntent(Context context, String title,String description, String uri, int photoId) {
        Intent intent = new Intent(context,PhotoDetailActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("uri",uri);
        intent.putExtra("photoId",photoId);
        intent.putExtra("description",description);
        return intent;
    }
}