package com.philip.studio.videoeditor.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Switch;
import android.widget.TextView;

import com.philip.studio.videoeditor.R;
import com.philip.studio.videoeditor.util.WallpaperUtil;

public class SettingsActivity extends AppCompatActivity {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch aSwitchWallpaper;
    TextView txtFollowFacebook, txtSubscribeYoutube;

    WallpaperUtil wallpaperUtil;
    String urlFacebook = "https://www.facebook.com/binhtinh.philip/";
    String urlYoutube = "https://www.youtube.com/channel/UCH6tg43n_FG5xZHlCfN360A";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);

        initView();

        if (wallpaperUtil.getWallpaperUtil()){
            aSwitchWallpaper.setChecked(true);
        }

        aSwitchWallpaper.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                wallpaperUtil.setWallpaperUtil(isChecked);
            }
            else{
                wallpaperUtil.setWallpaperUtil(false);
            }
        });

        txtFollowFacebook.setOnClickListener(v -> moveToSocialNetwork(urlFacebook));

        txtSubscribeYoutube.setOnClickListener(v -> moveToSocialNetwork(urlYoutube));
    }

    private void moveToSocialNetwork(String path){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(path));
        startActivity(intent);
    }

    public void onBack(View view){
        finish();
    }

    private void initView(){
        aSwitchWallpaper = findViewById(R.id.switch_wallpaper);
        txtFollowFacebook = findViewById(R.id.text_view_follow_facebook);
        txtSubscribeYoutube = findViewById(R.id.text_view_subscribe_youtube);

        wallpaperUtil = new WallpaperUtil(this);
    }
}