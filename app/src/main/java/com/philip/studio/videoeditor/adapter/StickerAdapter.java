package com.philip.studio.videoeditor.adapter;/*
//
// Project: Video Editor
// Created by ViettelStore on 1/3/2021.
// Copyright © 2021-2022 Philip Studio. All rights reserved.
//
*/

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.philip.studio.videoeditor.R;
import com.philip.studio.videoeditor.callback.OnItemStickerClickListener;

public class StickerAdapter extends RecyclerView.Adapter<StickerAdapter.ViewHolder> {

    int[] stickers;
    Context context;

    OnItemStickerClickListener onItemStickerClickListener;

    public StickerAdapter(int[] stickers, Context context) {
        this.stickers = stickers;
        this.context = context;
    }

    public void setOnItemStickerClickListener(OnItemStickerClickListener onItemStickerClickListener) {
        this.onItemStickerClickListener = onItemStickerClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sticker, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(stickers[position]).into(holder.imgSticker);

        holder.imgSticker.setOnClickListener(v -> {
            if (onItemStickerClickListener != null){
                onItemStickerClickListener.onItemClick(stickers[position]);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stickers.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgSticker;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgSticker = itemView.findViewById(R.id.item_sticker);
        }
    }
}
