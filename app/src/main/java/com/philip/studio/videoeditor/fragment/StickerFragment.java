package com.philip.studio.videoeditor.fragment;/*
//
// Project: Video Editor
// Created by ViettelStore on 1/3/2021.
// Copyright © 2021-2022 Philip Studio. All rights reserved.
//
*/

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.philip.studio.videoeditor.R;
import com.philip.studio.videoeditor.adapter.StickerAdapter;
import com.philip.studio.videoeditor.callback.OnItemStickerClickListener;
import com.philip.studio.videoeditor.event.StickerEvent;

import org.greenrobot.eventbus.EventBus;

public class StickerFragment extends Fragment {

    RecyclerView rVListStickers;

    int[] stickers = {
            R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4,
            R.drawable.image5, R.drawable.image6, R.drawable.image7, R.drawable.image8,
            R.drawable.image9, R.drawable.image10, R.drawable.image11, R.drawable.image12,
            R.drawable.image13, R.drawable.image14, R.drawable.image15, R.drawable.image16,
            R.drawable.image17, R.drawable.image18, R.drawable.image19, R.drawable.image20,
            R.drawable.image21, R.drawable.image22
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sticker, container, false);
        rVListStickers = view.findViewById(R.id.recycler_view_list_sticker);

        setUpRecyclerViewListSticker();
        return view;
    }

    private void setUpRecyclerViewListSticker(){
        rVListStickers.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        rVListStickers.setLayoutManager(gridLayoutManager);

        StickerAdapter adapter = new StickerAdapter(stickers, getContext());
        rVListStickers.setAdapter(adapter);

        adapter.setOnItemStickerClickListener(sticker -> EventBus.getDefault().post(new StickerEvent(sticker)));
    }
}
