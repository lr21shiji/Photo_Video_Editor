package com.philip.studio.videoeditor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.philip.studio.videoeditor.activity.ChooseImageActivity;
import com.philip.studio.videoeditor.activity.ChooseVideoOrImageActivity;
import com.philip.studio.videoeditor.activity.CollageActivity;
import com.philip.studio.videoeditor.activity.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    ImageView imgVideo, imgImage, imgCollage, imgSetting;

    private static final int REQUEST_PERMISSIONS = 123;
    int codeDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        initView();

        imgImage.setOnClickListener(listener);
        imgVideo.setOnClickListener(listener);
        imgCollage.setOnClickListener(listener);
        imgSetting.setOnClickListener(listener);
    }

    @SuppressLint("NonConstantResourceId")
    private final View.OnClickListener listener = v -> {
        switch (v.getId()){
            case R.id.image_view_video:
                codeDestination = 1;
                requestPermissions(codeDestination);
                break;
            case R.id.image_view_image:
                codeDestination = 2;
                requestPermissions(codeDestination);
                break;
            case R.id.image_view_collage:
                codeDestination = 3;
                requestPermissions(codeDestination);
                break;
            case R.id.image_view_setting:
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
        }
    };

    private void requestPermissions(int codeDestination) {
        if ((ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if ((ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) && (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))) {
                Toast.makeText(this, "Hello World", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMISSIONS);
            }
        } else {
            if (codeDestination == 1){
                Intent intent = new Intent(MainActivity.this, ChooseVideoOrImageActivity.class);
                startActivity(intent);
            }
            else if (codeDestination == 2){
                Intent intent = new Intent(MainActivity.this, ChooseImageActivity.class);
                startActivity(intent);
            }
            else if (codeDestination == 3){
                Intent intent = new Intent(MainActivity.this, CollageActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (codeDestination == 1){
                    Intent intent = new Intent(MainActivity.this, ChooseVideoOrImageActivity.class);
                    startActivity(intent);
                }
                else if (codeDestination == 2){
                    Intent intent = new Intent(MainActivity.this, ChooseImageActivity.class);
                    startActivity(intent);
                }
                else if (codeDestination == 3){
                    Intent intent = new Intent(MainActivity.this, CollageActivity.class);
                    startActivity(intent);
                }
            } else {
                requestPermissions(codeDestination);
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