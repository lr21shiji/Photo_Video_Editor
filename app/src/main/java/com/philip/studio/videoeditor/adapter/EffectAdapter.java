package com.philip.studio.videoeditor.adapter;/*
//
// Project: Video Editor
// Created by ViettelStore on 1/15/2021.
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

public class EffectAdapter extends RecyclerView.Adapter<EffectAdapter.ViewHolder> {

    int[] images;

    public EffectAdapter(int[] images) {
        this.images = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_effect, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imgOne.setImageResource(images[1]);
        holder.imgThree.setImageResource(images[3]);
        holder.imgTwo.setImageResource(images[2]);
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgOne, imgTwo, imgThree;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgOne = itemView.findViewById(R.id.item_image_view_effect_one);
            imgTwo = itemView.findViewById(R.id.item_image_view_effect_two);
            imgThree = itemView.findViewById(R.id.item_image_view_effect_three);
        }
    }
}
