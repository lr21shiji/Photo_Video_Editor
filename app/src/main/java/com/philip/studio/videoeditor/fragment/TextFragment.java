package com.philip.studio.videoeditor.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.philip.studio.videoeditor.R;
import com.philip.studio.videoeditor.adapter.ListFontAdapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import ja.burhanrashid52.photoeditor.OnPhotoEditorListener;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.SaveSettings;
import ja.burhanrashid52.photoeditor.TextStyleBuilder;
import ja.burhanrashid52.photoeditor.ViewType;

public class TextFragment extends Fragment implements OnPhotoEditorListener {

    PhotoEditorView photoEditorView;
    ImageView imgBack, imgInput, imgFont, imgFormat, imgCheck;
    BottomSheetBehavior<LinearLayout> bottomSheetBehavior;
    LinearLayout linearLayout;
    RecyclerView rVListFont;

    PhotoEditor photoEditor;
    String image, currentText;
    int color, fontText;

    public TextFragment(String image) {
        this.image = image;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text, container, false);
        initView(view);

        Uri imageUri = Uri.parse(image);
        photoEditorView.getSource().setImageURI(imageUri);
        setUpPhotoEditor();

        imgInput.setOnClickListener(listener);
        imgBack.setOnClickListener(listener);
        imgFont.setOnClickListener(listener);
        imgFormat.setOnClickListener(listener);
        imgCheck.setOnClickListener(listener);

        return view;
    }

    private void setUpPhotoEditor() {
        Typeface typefaceText = ResourcesCompat.getFont(getContext(), R.font.beyond_wonderland);
        photoEditor = new PhotoEditor.Builder(getContext(), photoEditorView)
                .setPinchTextScalable(true)
                .setDefaultTextTypeface(typefaceText)
                .build();
    }

    private View.OnClickListener listener = v -> {
        switch (v.getId()) {
            case R.id.image_view_input:
                InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                TextEditorDialogFragment textEditorDialogFragment = TextEditorDialogFragment.show((AppCompatActivity) getActivity());
                textEditorDialogFragment.setOnTextEditorListener((inputText, colorCode) -> {
                    final TextStyleBuilder styleBuilder = new TextStyleBuilder();
                    styleBuilder.withTextColor(colorCode);
                    color = colorCode;
                    currentText = inputText;
                    photoEditor.addText(inputText, styleBuilder);
                });
                break;
            case R.id.image_view_font:
                showBottomSheetListFont();
                break;
            case R.id.image_view_back:
                getFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_container, new ImageFragment(image))
                        .commit();
                break;
            case R.id.image_view_format:
                photoEditor.clearAllViews();
                TextStyleBuilder builder = new TextStyleBuilder();
                builder.withTextSize(30.0f);
                builder.withTextColor(color);
                Typeface typeface = ResourcesCompat.getFont(getContext(), fontText);
                builder.withTextFont(typeface);
                photoEditor.addText(currentText, builder);
                break;
            case R.id.image_view_check:
                saveToCacheImageFile();
                break;
        }
    };

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

    private void showBottomSheetListFont() {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        setUpRecyclerViewListFont();
    }

    private void setUpRecyclerViewListFont() {
        rVListFont.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rVListFont.setLayoutManager(layoutManager);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Roboto-Medium");
        arrayList.add("Permanentmarker");
        arrayList.add("Bebas");
        arrayList.add("Aileron");
        arrayList.add("Roboto-Thin");

        ListFontAdapter adapter = new ListFontAdapter(arrayList, getContext());
        rVListFont.setAdapter(adapter);

        adapter.setOnItemFontClickListener(font -> {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            photoEditor.clearAllViews();
            TextStyleBuilder builder = new TextStyleBuilder();

            fontText = font;
            Typeface typeface = ResourcesCompat.getFont(getContext(), font);
            builder.withTextColor(color);
            builder.withTextFont(typeface);
            photoEditor.addText(currentText, builder);
        });
    }

    private void initView(View view) {
        photoEditorView = view.findViewById(R.id.photo_editor_view);
        imgBack = view.findViewById(R.id.image_view_back);
        imgInput = view.findViewById(R.id.image_view_input);
        imgFont = view.findViewById(R.id.image_view_font);
        imgFormat = view.findViewById(R.id.image_view_format);
        rVListFont = view.findViewById(R.id.recycler_view_list_font);
        imgCheck = view.findViewById(R.id.image_view_check);

        linearLayout = view.findViewById(R.id.linear_layout_font);
        bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
    }

    @Override
    public void onEditTextChangeListener(View rootView, String text, int colorCode) {
        TextEditorDialogFragment dialogFragment = TextEditorDialogFragment.show((AppCompatActivity) getActivity(), text, colorCode);
        dialogFragment.setOnTextEditorListener((inputText, colorCode1) -> {
            TextStyleBuilder builder = new TextStyleBuilder();
            builder.withTextColor(colorCode1);
            photoEditor.editText(rootView, inputText, builder);
        });
    }

    @Override
    public void onAddViewListener(ViewType viewType, int numberOfAddedViews) {

    }

    @Override
    public void onRemoveViewListener(ViewType viewType, int numberOfAddedViews) {

    }

    @Override
    public void onStartViewChangeListener(ViewType viewType) {

    }

    @Override
    public void onStopViewChangeListener(ViewType viewType) {

    }
}
