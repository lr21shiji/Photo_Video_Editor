package com.philip.studio.videoeditor.fragment;/*
//
// Project: Video Editor
// Created by ViettelStore on 1/8/2021.
// Copyright © 2021-2022 Philip Studio. All rights reserved.
//
*/

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.philip.studio.videoeditor.R;
import com.philip.studio.videoeditor.adapter.ColorPickerAdapter;
import com.philip.studio.videoeditor.adapter.GradientAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class BackgroundFragment extends Fragment {

    ImageView imgImage, imgCheck, imgClear, imgOpenGallery, imgBackground,
            imgOriginal, imgBlurOne, imgBlurTwo, imgBlurThree, imgBlurFour;
    RecyclerView rVListGradient, rVListColor;

    String image;
    private static final int REQUEST_CODE = 123;
    int[] backgroundColors = {R.drawable.gradient1, R.drawable.gradient2, R.drawable.gradient3, R.drawable.gradient4,
            R.drawable.gradient5, R.drawable.gradient6, R.drawable.gradient7,
            R.drawable.gradient8, R.drawable.gradient9, R.drawable.gradient10};

    public BackgroundFragment(String image) {
        this.image = image;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_background, container, false);
        initView(view);

        Glide.with(getContext()).load(image).into(imgImage);
        setUpRecyclerViewListGradient();

        setUpRecyclerViewListColor();

        Glide.with(getContext()).load(image).into(imgOriginal);
        setUpImageBlur(10, imgBackground);
        setUpImageBlur(5, imgBlurOne);
        setUpImageBlur(10, imgBlurTwo);
        setUpImageBlur(22, imgBlurThree);
        setUpImageBlur(40, imgBlurFour);


        imgCheck.setOnClickListener(listener);
        imgClear.setOnClickListener(listener);
        imgOpenGallery.setOnClickListener(listener);
        imgBlurOne.setOnClickListener(listener);
        imgBlurTwo.setOnClickListener(listener);
        imgBlurThree.setOnClickListener(listener);
        imgBlurFour.setOnClickListener(listener);

        return view;
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.image_view_check:
                    drawAndSaveBitmap(imgImage);
                    break;
                case R.id.image_view_clear:
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame_layout_container, new ImageFragment(image))
                            .commit();
                    break;
                case R.id.image_view_open_gallery:
                    openGallery();
                    break;
                case R.id.image_view_blur_one:
                    setUpImageBlur(1, imgBackground);
                    break;
                case R.id.image_view_blur_two:
                    setUpImageBlur(6, imgBackground);
                    break;
                case R.id.image_view_blur_three:
                    setUpImageBlur(8, imgBackground);
                    break;
                case R.id.image_view_blur_four:
                    setUpImageBlur(10, imgBackground);
                    break;
            }
        }
    };

    private void setUpImageBlur(int blur, ImageView imageView){
        Glide.with(getContext()).load(image)
                .transform(new BlurTransformation(blur))
                .into(imageView);
    }

    private void drawAndSaveBitmap(View view){
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        String nameFile = "IMG_" + System.currentTimeMillis() + ".png";
        File file = new File(getContext().getCacheDir(), nameFile);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout_container, new ImageFragment(file.getAbsolutePath()))
                    .commit();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        String nameFile = "IMG_" + System.currentTimeMillis() + ".png";
//        MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmap, nameFile, "Description");
    }

    private void openGallery(){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK && requestCode == REQUEST_CODE && data != null){
            Uri uri = data.getData();
            Glide.with(getContext()).load(uri).into(imgBackground);
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setUpRecyclerViewListGradient(){
        rVListGradient.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rVListGradient.setLayoutManager(layoutManager);

        GradientAdapter adapter = new GradientAdapter(backgroundColors);
        rVListGradient.setAdapter(adapter);

        adapter.setOnItemBackgroundGradientListener(res -> imgImage.setBackground(getContext().getDrawable(res)));
    }

    private void setUpRecyclerViewListColor(){
        rVListColor.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rVListColor.setLayoutManager(layoutManager);

        ColorPickerAdapter adapter = new ColorPickerAdapter(getContext());
        rVListColor.setAdapter(adapter);

        adapter.setOnColorPickerClickListener(colorCode -> imgBackground.setBackgroundColor(colorCode));
    }

    private void initView(View view){
        imgImage = view.findViewById(R.id.image_view_preview);
        imgCheck = view.findViewById(R.id.image_view_check);
        imgClear = view.findViewById(R.id.image_view_clear);
        imgOpenGallery = view.findViewById(R.id.image_view_open_gallery);
        imgBackground = view.findViewById(R.id.image_view_background);
        imgOriginal = view.findViewById(R.id.image_view_original);
        imgBlurOne = view.findViewById(R.id.image_view_blur_one);
        imgBlurTwo = view.findViewById(R.id.image_view_blur_two);
        imgBlurThree = view.findViewById(R.id.image_view_blur_three);
        imgBlurFour = view.findViewById(R.id.image_view_blur_four);
        rVListGradient = view.findViewById(R.id.recycler_view_list_gradients);
        rVListColor = view.findViewById(R.id.recycler_view_list_color);
    }
}

