package com.example.newsfeedapplication;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.AppCompatImageView;

public class MyHolder extends RecyclerView.ViewHolder {
    ImageView mImaeView;
    TextView mTitle, mDes;

    public MyHolder(@NonNull ImageView itemView) {
        super(itemView);

        this.mImaeView = itemView.findViewById(R.id.imageIv);
        this.mTitle = itemView.findViewById(R.id.titleTv);
        this.mDes = itemView.findViewById(R.id.descriptionTv);

    }
}
