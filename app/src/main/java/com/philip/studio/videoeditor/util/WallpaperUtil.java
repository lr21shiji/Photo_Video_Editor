package com.philip.studio.videoeditor.util;/*
//
// Project: Video Editor
// Created by ViettelStore on 1/7/2021.
// Copyright © 2021-2022 Philip Studio. All rights reserved.
//
*/

import android.content.Context;
import android.content.SharedPreferences;

public class WallpaperUtil {
    private SharedPreferences preferences;

    public WallpaperUtil(Context context) {
        preferences = context.getSharedPreferences("wallpaper", Context.MODE_PRIVATE);
    }

    public void setWallpaperUtil(boolean isWallpaper){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("wallpaper", isWallpaper);
        editor.apply();
    }

    public boolean getWallpaperUtil(){
        return preferences.getBoolean("wallpaper", false);
    }
}
