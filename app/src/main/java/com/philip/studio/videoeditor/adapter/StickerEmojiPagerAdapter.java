package com.philip.studio.videoeditor.adapter;/*
//
// Project: Video Editor
// Created by ViettelStore on 1/3/2021.
// Copyright © 2021-2022 Philip Studio. All rights reserved.
//
*/

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.philip.studio.videoeditor.fragment.EmojiFragment;
import com.philip.studio.videoeditor.fragment.StickerFragment;

public class StickerEmojiPagerAdapter extends FragmentPagerAdapter {


    public StickerEmojiPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new EmojiFragment();
            case 1: return new StickerFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "Emoji";
            case 1: return "Sticker";
        }
        return null;
    }
}
