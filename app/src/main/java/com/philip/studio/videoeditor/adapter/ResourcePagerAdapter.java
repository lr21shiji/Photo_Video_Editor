package com.philip.studio.videoeditor.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.philip.studio.videoeditor.fragment.AllFragment;
import com.philip.studio.videoeditor.fragment.ListImagesFragment;
import com.philip.studio.videoeditor.fragment.ListVideosFragment;

public class ResourcePagerAdapter extends FragmentPagerAdapter {

    public ResourcePagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ListVideosFragment();
            case 1:
                return new ListImagesFragment();
            case 2:
                return new AllFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "your video";
            case 1:
                return "image";
            case 2:
                return "All";
        }
        return null;
    }
}
