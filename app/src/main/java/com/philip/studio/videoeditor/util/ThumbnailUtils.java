package com.philip.studio.videoeditor.util;/*
//
// Project: Video Editor
// Created by ViettelStore on 1/1/2021.
// Copyright © 2021-2022 Philip Studio. All rights reserved.
//
*/

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;

public class ThumbnailUtils {

    public static Bitmap createThumbnail(String path){
        Bitmap thumbnailBitmap = null;
        File file = new File(path);
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            thumbnailBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            return null;
        }

        return thumbnailBitmap;
    }
}
