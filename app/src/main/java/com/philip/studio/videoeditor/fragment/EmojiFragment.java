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
import com.philip.studio.videoeditor.adapter.EmojiAdapter;
import com.philip.studio.videoeditor.event.EmojiEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import ja.burhanrashid52.photoeditor.PhotoEditor;

public class EmojiFragment extends Fragment {

    RecyclerView rVListEmoji;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emoji, container, false);
        rVListEmoji = view.findViewById(R.id.recycler_view_list_emoji);

        setUpRecyclerViewListEmoji();

        return view;
    }

    public void setUpRecyclerViewListEmoji() {
        rVListEmoji.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 5);
        rVListEmoji.setLayoutManager(gridLayoutManager);

        ArrayList<String> listEmoji = PhotoEditor.getEmojis(getContext());
        EmojiAdapter emojiAdapter = new EmojiAdapter(listEmoji, getContext());
        rVListEmoji.setAdapter(emojiAdapter);

        emojiAdapter.setOnItemEmojiClickListener(emoji -> EventBus.getDefault().post(new EmojiEvent(emoji)));
    }
}
