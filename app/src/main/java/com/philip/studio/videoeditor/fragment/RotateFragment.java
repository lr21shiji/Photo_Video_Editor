package com.philip.studio.videoeditor.fragment;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.philip.studio.videoeditor.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class RotateFragment extends Fragment {

    String image;
    int click = 0;
    float degrees = 0;
    boolean isCheckScale = false, isCheckRotateRadius = false;

    ImageView imgImage, imgRotate, imgScale, imgRotateRadius, imgFlip, imgCheck, imgClear;
    SeekBar sBControl;
    TextView txtDisplay;

    public RotateFragment(String image) {
        this.image = image;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rotate, container, false);
        initView(view);

        Glide.with(getContext()).load(image).into(imgImage);

        imgRotate.setOnClickListener(listener);
        imgScale.setOnClickListener(listener);
        imgRotateRadius.setOnClickListener(listener);
        imgFlip.setOnClickListener(listener);
        imgCheck.setOnClickListener(listener);
        imgClear.setOnClickListener(listener);
        sBControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    txtDisplay.setText(String.valueOf(progress));
                    if (isCheckRotateRadius) {
                        degrees = progress;
                        imgImage.setRotation(degrees);
                    } else if (isCheckScale) {
                        imgImage.setScaleX(progress * 0.1f);
                        imgImage.setScaleY(progress * 0.1f);
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        return view;
    }

    private View.OnClickListener listener = v -> {
        switch (v.getId()) {
            case R.id.image_view_flip:
                if (imgImage.getScaleX() != -1) {
                    imgImage.setScaleX(-1);
                } else {
                    imgImage.setScaleX(1);
                }
                break;
            case R.id.image_view_rotate:
                setDisplay(View.GONE);
                click++;
                degrees = 90 * click;
                imgImage.setRotation(degrees);
                break;
            case R.id.image_view_rotate_with_radius:
                isCheckRotateRadius = true;
                isCheckScale = false;
                setDisplay(View.VISIBLE);
                break;
            case R.id.image_view_scale:
                isCheckScale = true;
                isCheckRotateRadius = false;
                setDisplay(View.VISIBLE);
                break;
            case R.id.image_view_check:
                Bitmap bitmap = ((BitmapDrawable) imgImage.getDrawable()).getBitmap();
                saveImageFile(bitmap, degrees);
                break;
            case R.id.image_view_clear:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout_container, new ImageFragment(image))
                        .commit();
                break;
        }
    };

    private void saveImageFile(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees, bitmap.getWidth(), bitmap.getHeight());
        Bitmap rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);

        Matrix matrix1 = new Matrix();
        matrix1.setScale(-1.0f, 1.0f, bitmap.getWidth(), bitmap.getHeight());
        Bitmap flipBitmap = Bitmap.createBitmap(rotateBitmap, 0, 0, rotateBitmap.getWidth(), rotateBitmap.getHeight(), matrix1, false);

        String nameFile = "IMG_" + System.currentTimeMillis() + ".png";
        File file = new File(getContext().getCacheDir(), nameFile);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            flipBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout_container, new ImageFragment(file.getAbsolutePath()))
                    .commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setDisplay(int visibility) {
        sBControl.setVisibility(visibility);
        txtDisplay.setVisibility(visibility);
    }

    private void initView(View view) {
        imgImage = view.findViewById(R.id.image_view_image);
        imgRotate = view.findViewById(R.id.image_view_rotate);
        imgScale = view.findViewById(R.id.image_view_scale);
        sBControl = view.findViewById(R.id.seek_bar_control);
        txtDisplay = view.findViewById(R.id.text_view_display);
        imgRotateRadius = view.findViewById(R.id.image_view_rotate_with_radius);
        imgFlip = view.findViewById(R.id.image_view_flip);
        imgCheck = view.findViewById(R.id.image_view_check);
        imgClear = view.findViewById(R.id.image_view_clear);

        setDisplay(View.GONE);

        sBControl.setMax(180);
    }
}
