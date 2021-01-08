package com.philip.studio.videoeditor.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.philip.studio.videoeditor.R;

public class EffectFragment extends Fragment {

    String image;

    ImageView imgImage;

    public EffectFragment(String image) {
        this.image = image;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_effect, container, false);
        initView(view);

        Glide.with(getContext()).load(image).into(imgImage);

        return view;
    }

    private void initView(View view){
        imgImage = view.findViewById(R.id.image_view_image);
    }
}
