package com.philip.studio.videoeditor.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.philip.studio.videoeditor.R;
import com.philip.studio.videoeditor.adapter.FilterAdapter;
import com.philip.studio.videoeditor.callback.OnItemFilterListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.PhotoFilter;
import ja.burhanrashid52.photoeditor.SaveSettings;

public class FilterFragment extends Fragment implements OnItemFilterListener {

    RecyclerView rVListFilter;
    PhotoEditorView photoEditorView;
    PhotoEditor photoEditor;
    ImageView imgCheck, imgClear;

    String image;
    List<Pair<String, PhotoFilter>> pairList = new ArrayList<>();

    public FilterFragment(String image) {
        this.image = image;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        initView(view);

        Uri uri = Uri.parse(image);
        photoEditorView.getSource().setImageURI(uri);
        setUpPhotoEditor();

        rVListFilter.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        rVListFilter.setLayoutManager(layoutManager);
        rVListFilter.getRecycledViewPool().setMaxRecycledViews(0, 0);
        

        setupFilters(image);
        FilterAdapter adapter = new FilterAdapter(this, getContext(), pairList, image);
        rVListFilter.setAdapter(adapter);

        imgCheck.setOnClickListener(v -> saveToCacheImageFile());
        imgClear.setOnClickListener(v -> getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_container, new ImageFragment(image))
                .commit());
        return view;
    }

    private void saveToCacheImageFile() {
        String nameFile = "IMG_" + System.currentTimeMillis() + ".png";
        File file = new File(getContext().getCacheDir(), nameFile);
        try {
            file.createNewFile();
            SaveSettings saveSettings = new SaveSettings.Builder()
                    .setClearViewsEnabled(true)
                    .setTransparencyEnabled(true)
                    .build();

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            photoEditor.saveAsFile(file.getAbsolutePath(), saveSettings, new PhotoEditor.OnSaveListener() {
                @Override
                public void onSuccess(@NonNull String imagePath) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.frame_layout_container, new ImageFragment(imagePath))
                            .commit();
                }

                @Override
                public void onFailure(@NonNull Exception exception) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setUpPhotoEditor() {
        Typeface typefaceText = ResourcesCompat.getFont(getContext(), R.font.beyond_wonderland);

        photoEditor = new PhotoEditor.Builder(getContext(), photoEditorView)
                .setPinchTextScalable(true)
                .setDefaultTextTypeface(typefaceText)
                .build();
    }

    private void setupFilters(String image) {
        pairList.add(new Pair<>(image, PhotoFilter.NONE));
        pairList.add(new Pair<>(image, PhotoFilter.AUTO_FIX));
        pairList.add(new Pair<>(image, PhotoFilter.BRIGHTNESS));
        pairList.add(new Pair<>(image, PhotoFilter.CONTRAST));
        pairList.add(new Pair<>(image, PhotoFilter.DOCUMENTARY));
        pairList.add(new Pair<>(image, PhotoFilter.DUE_TONE));
        pairList.add(new Pair<>(image, PhotoFilter.FILL_LIGHT));
        pairList.add(new Pair<>(image, PhotoFilter.FISH_EYE));
        pairList.add(new Pair<>(image, PhotoFilter.GRAIN));
        pairList.add(new Pair<>(image, PhotoFilter.GRAY_SCALE));
        pairList.add(new Pair<>(image, PhotoFilter.LOMISH));
        pairList.add(new Pair<>(image, PhotoFilter.NEGATIVE));
        pairList.add(new Pair<>(image, PhotoFilter.POSTERIZE));
        pairList.add(new Pair<>(image, PhotoFilter.SATURATE));
        pairList.add(new Pair<>(image, PhotoFilter.SEPIA));
        pairList.add(new Pair<>(image, PhotoFilter.SHARPEN));
        pairList.add(new Pair<>(image, PhotoFilter.TEMPERATURE));
        pairList.add(new Pair<>(image, PhotoFilter.TINT));
        pairList.add(new Pair<>(image, PhotoFilter.VIGNETTE));
        pairList.add(new Pair<>(image, PhotoFilter.CROSS_PROCESS));
        pairList.add(new Pair<>(image, PhotoFilter.BLACK_WHITE));
        pairList.add(new Pair<>(image, PhotoFilter.FLIP_HORIZONTAL));
        pairList.add(new Pair<>(image, PhotoFilter.FLIP_VERTICAL));
        pairList.add(new Pair<>(image, PhotoFilter.ROTATE));
    }

    @Override
    public void onItemFilterSelected(PhotoFilter photoFilter) {
        photoEditor.setFilterEffect(photoFilter);
    }

    private void initView(View view) {
        rVListFilter = view.findViewById(R.id.recycler_view_list_filter);
        photoEditorView = view.findViewById(R.id.photo_editor_view);
        imgCheck = view.findViewById(R.id.image_view_check);
        imgClear = view.findViewById(R.id.image_view_clear);
    }
}
