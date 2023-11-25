package com.karpovich.hush_test.ui.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.karpovich.hush_test.R;
import com.karpovich.hush_test.data.entities.Photo;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private List<Photo> photos = new ArrayList<>();

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.photo_item,
                parent,
                false
        );
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        Photo photo = photos.get(position);
        Glide.with(holder.itemView)
                .load(Uri.parse(photo.getUri().toString()))
                .into(holder.imageView);
        String photoTitle = photo.getTitle();
        holder.photoTitleTextView.setText(photoTitle);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    static class PhotoViewHolder extends RecyclerView.ViewHolder{
        private final ImageView imageView;
        private final TextView photoTitleTextView;
        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.photoImageView);
            photoTitleTextView = itemView.findViewById(R.id.photoTitleTextView);
        }
    }
}
