package com.philip.studio.videoeditor.effect;/*
//
// Project: Video Editor
// Created by ViettelStore on 1/16/2021.
// Copyright © 2021-2022 Philip Studio. All rights reserved.
//
*/

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class Glitch {
    static double amount, seed, iterations;
    static byte[] imageByte;
    static int jpgHeaderLength;

    public static Bitmap glitch(Bitmap bitmap, int amount1, int seed1, int iterations1, int quality1) {
        amount = amount1;
        seed = seed1;
        iterations = iterations1;
        normalized();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality1, outputStream);
        imageByte = outputStream.toByteArray();
        jpgHeaderLength = getJpegHeaderSize();
        for (int i=0, len=(int)iterations; i<len; i++){
            glitchJpegBytes(i);
        }

        Bitmap glitchBitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length);
        return glitchBitmap;
    }

    private static void glitchJpegBytes (int i) {
        int maxIndex = imageByte.length - jpgHeaderLength - 4;
        double pxMin = ( maxIndex / iterations * i);
        double pxMax = ( maxIndex / iterations * ( i + 1 ));
        double delta = pxMax - pxMin;
        double pxIndex = ( pxMin + delta * seed);
        if ( pxIndex > maxIndex ) {
            pxIndex = maxIndex;
        }
        int index = (int)Math.floor( jpgHeaderLength + pxIndex );
        imageByte[index] = (byte)Math.floor( amount * 256 );
    }

    private static int getJpegHeaderSize() {
        int result = 417;
        for(int i = 0,len = imageByte.length; i < len; i++ ) {
            if ( imageByte[i] == 255 && imageByte[i + 1] == 218 ) {
                result = i + 2;
                break;
            }
        }
        return result;
    }

    public static void normalized(){
        seed = seed/100;
        amount = amount/100;
    }
}
