package com.philip.studio.videoeditor.adapter;/*
//
// Project: Video Editor
// Created by ViettelStore on 1/9/2021.
// Copyright © 2021-2022 Philip Studio. All rights reserved.
//
*/

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.philip.studio.videoeditor.R;

public class GradientAdapter extends RecyclerView.Adapter<GradientAdapter.DemoViewHolder> {

    int[] backgrounds;
    OnItemBackgroundGradientListener onItemBackgroundGradientListener;

    public GradientAdapter(int[] backgrounds) {
        this.backgrounds = backgrounds;
    }

    public void setOnItemBackgroundGradientListener(OnItemBackgroundGradientListener onItemBackgroundGradientListener) {
        this.onItemBackgroundGradientListener = onItemBackgroundGradientListener;
    }

    @NonNull
    @Override
    public DemoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_demo, parent, false);
        return new DemoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DemoViewHolder holder, int position) {
        holder.imageView.setImageResource(backgrounds[position]);

        holder.imageView.setOnClickListener(v -> {
            if (onItemBackgroundGradientListener != null){
                onItemBackgroundGradientListener.onItemClick(backgrounds[position]);
            }
        });
    }

    @Override
    public int getItemCount() {
        return backgrounds.length;
    }

    public class DemoViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;

        public DemoViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.item_background);
        }
    }

    public interface OnItemBackgroundGradientListener{
        void onItemClick(int res);
    }
}
