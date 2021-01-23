package com.philip.studio.videoeditor.adapter;/*
//
// Project: Video Editor
// Created by ViettelStore on 1/11/2021.
// Copyright © 2021-2022 Philip Studio. All rights reserved.
//
*/

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daasuu.gpuv.egl.filter.GlFilter;
import com.philip.studio.videoeditor.R;
import com.philip.studio.videoeditor.callback.FilterAdjuster;
import com.philip.studio.videoeditor.callback.OnItemVideoFilterClickListener;
import com.philip.studio.videoeditor.filter.FilterType;

import java.util.List;

public class VideoFilterAdapter extends RecyclerView.Adapter<VideoFilterAdapter.ViewHolder> {

    List<FilterType> filterTypeList;
    Context context;

    OnItemVideoFilterClickListener onItemVideoFilterClickListener;

    public VideoFilterAdapter(List<FilterType> filterTypeList, Context context) {
        this.filterTypeList = filterTypeList;
        this.context = context;
    }

    public void setOnItemVideoFilterClickListener(OnItemVideoFilterClickListener onItemVideoFilterClickListener) {
        this.onItemVideoFilterClickListener = onItemVideoFilterClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_video_filter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtNameFilter.setText(filterTypeList.get(position).name().replace("_", " "));

        holder.txtNameFilter.setOnClickListener(v -> {
            GlFilter filter = FilterType.createGlFilter(filterTypeList.get(position), context);
            FilterAdjuster adjuster = FilterType.createFilterAdjuster(filterTypeList.get(position));
            if (onItemVideoFilterClickListener != null){
                onItemVideoFilterClickListener.onItemClick(filter, adjuster);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filterTypeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtNameFilter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNameFilter = itemView.findViewById(R.id.item_filter);
        }
    }

}
