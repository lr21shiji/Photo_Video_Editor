package com.philip.studio.videoeditor.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.tabs.TabLayout;
import com.philip.studio.videoeditor.R;
import com.philip.studio.videoeditor.activity.ShareImageActivity;
import com.philip.studio.videoeditor.adapter.StickerEmojiPagerAdapter;
import com.philip.studio.videoeditor.callback.OnCropImageListener;
import com.philip.studio.videoeditor.event.EmojiEvent;
import com.philip.studio.videoeditor.event.StickerEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;

import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.SaveSettings;

public class ImageFragment extends Fragment {

    ImageView imgFilter, imgEffect, imgBackground,
            imgCrop, imgEmoji, imgText, imgRotate, imgBack;
    TextView txtSaved;
    PhotoEditorView photoEditorView;
    LinearLayout linearLayout;
    ViewPager viewPager;
    TabLayout tabLayout;

    Uri imageUri;
    PhotoEditor photoEditor;
    BottomSheetBehavior<LinearLayout> bottomSheetBehavior;
    OnCropImageListener cropImageListener;
    String image;

    public ImageFragment(String image) {
        this.image = image;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        initView(view);

        imageUri = Uri.parse(image);
        photoEditorView.getSource().setImageURI(imageUri);
        setUpPhotoEditor();

        imgText.setOnClickListener(listener);
        imgEmoji.setOnClickListener(listener);
        imgBack.setOnClickListener(listener);
        imgFilter.setOnClickListener(listener);
        imgRotate.setOnClickListener(listener);
        imgEffect.setOnClickListener(listener);
        imgCrop.setOnClickListener(listener);
        txtSaved.setOnClickListener(listener);

        return view;
    }

    private View.OnClickListener listener = v -> {
        switch (v.getId()) {
            case R.id.image_view_text:
                getFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_container, new TextFragment(image))
                        .commit();
                break;
            case R.id.image_view_emoji:
                showBottomSheetEmoji();
                break;
            case R.id.image_view_back:
                getActivity().finish();
                break;
            case R.id.image_view_filter:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout_container, new FilterFragment(image))
                        .commit();
                break;
            case R.id.image_view_rotate:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout_container, new RotateFragment(image))
                        .commit();
                break;
            case R.id.image_view_effect:
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_layout_container, new EffectFragment(image))
                        .commit();
                break;
            case R.id.image_view_crop:
                cropImageListener.onCropImage(imageUri);
                break;
            case R.id.text_view_saved:
                saveImageFile();
                break;
        }
    };

    private void saveImageFile() {
        File cacheFile = new File(String.valueOf(getContext().getCacheDir()));
        File[] files = cacheFile.listFiles();
        if (files != null) {
            for (File file : files) {
                file.delete();
            }
        }

        String nameFile = "IMG_" + System.currentTimeMillis() + ".png";
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Video Editor/" + nameFile);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SaveSettings saveSettings = new SaveSettings.Builder()
                .setTransparencyEnabled(true)
                .setClearViewsEnabled(true)
                .setCompressFormat(Bitmap.CompressFormat.PNG)
                .build();

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        photoEditor.saveAsFile(file.getAbsolutePath(), saveSettings, new PhotoEditor.OnSaveListener() {
            @Override
            public void onSuccess(@NonNull String imagePath) {
                Bitmap bitmap = stringToBitmap(imagePath);
                MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmap, nameFile, "Description");
                Intent intent = new Intent(getContext(), ShareImageActivity.class);
                intent.putExtra("image", imagePath);
                startActivity(intent);
            }

            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getContext(), exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Bitmap stringToBitmap(String path) {
        try {
            byte[] encodeByte = Base64.decode(path, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }

    private void setUpPhotoEditor() {
        Typeface typefaceText = ResourcesCompat.getFont(getContext(), R.font.beyond_wonderland);

        photoEditor = new PhotoEditor.Builder(getContext(), photoEditorView)
                .setPinchTextScalable(true)
                .setDefaultTextTypeface(typefaceText)
                .build();
    }

    public void showBottomSheetEmoji() {
        bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        assert getFragmentManager() != null;
        StickerEmojiPagerAdapter adapter = new StickerEmojiPagerAdapter(getFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);

        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(EmojiEvent emojiEvent){
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        photoEditor.addEmoji(emojiEvent.getEmoji());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(StickerEvent stickerEvent){
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        int sticker = stickerEvent.getSticker();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), sticker);
        photoEditor.addImage(bitmap);
    }

    private void initView(View view) {
        photoEditorView = view.findViewById(R.id.photo_editor_view);
        txtSaved = view.findViewById(R.id.text_view_saved);
        imgFilter = view.findViewById(R.id.image_view_filter);
        imgBackground = view.findViewById(R.id.image_view_background);
        imgEffect = view.findViewById(R.id.image_view_effect);
        imgCrop = view.findViewById(R.id.image_view_crop);
        imgText = view.findViewById(R.id.image_view_text);
        imgEmoji = view.findViewById(R.id.image_view_emoji);
        imgRotate = view.findViewById(R.id.image_view_rotate);
        imgBack = view.findViewById(R.id.image_view_back);
        viewPager = view.findViewById(R.id.view_pager);
        tabLayout = view.findViewById(R.id.tab_layout);

        linearLayout = view.findViewById(R.id.linear_layout_emoji);
        bottomSheetBehavior = BottomSheetBehavior.from(linearLayout);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        cropImageListener = (OnCropImageListener) context;
    }
}
