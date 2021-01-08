package com.philip.studio.videoeditor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.philip.studio.videoeditor.activity.ChooseImageActivity;
import com.philip.studio.videoeditor.activity.ChooseVideoOrImageActivity;
import com.philip.studio.videoeditor.activity.CollageActivity;
import com.philip.studio.videoeditor.activity.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    ImageView imgVideo, imgImage, imgCollage, imgSetting;

    private static final int REQUEST_PERMISSIONS = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        initView();

        imgImage.setOnClickListener(v -> requestPermissions());
        imgVideo.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ChooseVideoOrImageActivity.class);
            startActivity(intent);
        });

        imgCollage.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CollageActivity.class);
            startActivity(intent);
        });

        imgSetting.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }

    private void requestPermissions() {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {

            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        } else {
            Intent intent = new Intent(MainActivity.this, ChooseImageActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MainActivity.this, ChooseImageActivity.class);
                startActivity(intent);
            } else {
                requestPermissions();
            }
        }
    }

    private void initView() {
        imgVideo = findViewById(R.id.image_view_video);
        imgImage = findViewById(R.id.image_view_image);
        imgCollage = findViewById(R.id.image_view_collage);
        imgSetting = findViewById(R.id.image_view_setting);
    }
}