package com.philip.studio.videoeditor.fragment;/*
//
// Project: Video Editor
// Created by ViettelStore on 1/16/2021.
// Copyright © 2021-2022 Philip Studio. All rights reserved.
//
*/

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.naver.android.helloyako.imagecrop.view.ImageCropView;
import com.philip.studio.videoeditor.R;

public class CropFragment extends Fragment {

    String image;

    ImageCropView imageCropView;
    public CropFragment(String image) {
        this.image = image;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crop, container, false);
        initView(view);

        imageCropView.setImageFilePath(image);
        imageCropView.setAspectRatio(1, 1);
        return view;
    }

    private void initView(View view){
        imageCropView = view.findViewById(R.id.image_crop_view);
    }
}
