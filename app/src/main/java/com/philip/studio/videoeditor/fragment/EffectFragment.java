package com.philip.studio.videoeditor.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.philip.studio.videoeditor.R;
import com.philip.studio.videoeditor.adapter.EffectAdapter;

public class EffectFragment extends Fragment {

    String image;
    int[] images = {R.drawable.dieu_anh_biet, R.drawable.lang_le_buong, R.drawable.noi_buon_dem_dong,
            R.drawable.image22, R.drawable.image2, R.drawable.image19, R.drawable.image18,
            R.drawable.image10, R.drawable.image13};

    ImageView imgImage, imgBack;
    TextView txtMalfunction, txtRhythm, txtDistortion, txtStyle, txtCelebrate;
    RecyclerView rVListEffect;

    public EffectFragment(String image) {
        this.image = image;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_effect, container, false);
        initView(view);

        Glide.with(getContext()).load(image).into(imgImage);

        setUpRecyclerViewListEffect();

        imgBack.setOnClickListener(listener);

        return view;
    }

    @SuppressLint("NonConstantResourceId")
    private final View.OnClickListener listener = v -> {
        switch (v.getId()) {
            case R.id.image_view_back:
                showAlertDialog();
                break;
            case R.id.text_view_malfunction:
                Toast.makeText(getContext(), "Malfunction", Toast.LENGTH_SHORT).show();
                break;
            case R.id.text_view_rhythm:
                Toast.makeText(getContext(), "Rhythm", Toast.LENGTH_SHORT).show();
                break;
            case R.id.text_view_distortion:
                Toast.makeText(getContext(), "Distortion", Toast.LENGTH_SHORT).show();
                break;
            case R.id.text_view_style:
                Toast.makeText(getContext(), "Style", Toast.LENGTH_SHORT).show();
                break;
            case R.id.text_view_celebrate:
                Toast.makeText(getContext(), "Celebrate", Toast.LENGTH_SHORT).show();
                break;
        }
    };

    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Do you really want exit ?");
        builder.setPositiveButton("Ok", (dialog, which) -> getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_container, new ImageFragment(image))
                .commit());

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void setUpRecyclerViewListEffect() {
        rVListEffect.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rVListEffect.setLayoutManager(layoutManager);

        EffectAdapter effectAdapter = new EffectAdapter(images);
        rVListEffect.setAdapter(effectAdapter);
    }

    private void initView(View view) {
        imgImage = view.findViewById(R.id.image_view_image);
        imgBack = view.findViewById(R.id.image_view_back);
        txtMalfunction = view.findViewById(R.id.text_view_malfunction);
        txtCelebrate = view.findViewById(R.id.text_view_celebrate);
        txtStyle = view.findViewById(R.id.text_view_style);
        txtRhythm = view.findViewById(R.id.text_view_rhythm);
        txtDistortion = view.findViewById(R.id.text_view_distortion);
        rVListEffect = view.findViewById(R.id.recycler_view_list_effect);
    }
}
