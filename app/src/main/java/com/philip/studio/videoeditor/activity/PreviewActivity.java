package com.philip.studio.videoeditor.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.jcmore2.collage.CollageView;
import com.philip.studio.videoeditor.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PreviewActivity extends AppCompatActivity {

    Button btnSetWallpaper, btnOpenGallery;
    CollageView collageView;

    private static final int REQUEST_CODE = 123;
    List<Bitmap> listImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_preview);

        initView();

        btnSetWallpaper.setOnClickListener(v -> setWallpaper());
        btnOpenGallery.setOnClickListener(v -> openGallery());
    }

    private void setWallpaper() {
        Bitmap bitmap = Bitmap.createBitmap(collageView.getWidth(), collageView.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        collageView.draw(canvas);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE && data != null) {
            if (data.getClipData() != null){
                for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                    Uri uri = data.getClipData().getItemAt(i).getUri();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        listImages.add(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if (data.getData() != null){
                Uri uri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    listImages.add(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            collageView.createCollageBitmaps(listImages);
        }
    }


    private void initView() {
        btnSetWallpaper = findViewById(R.id.button_set_wallpaper);
        btnOpenGallery = findViewById(R.id.button_open_gallery);
        collageView = findViewById(R.id.collage_view);

        listImages = new ArrayList<>();
    }
}