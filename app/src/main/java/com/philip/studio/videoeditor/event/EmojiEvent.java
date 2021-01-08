package com.philip.studio.videoeditor.event;/*
//
// Project: Video Editor
// Created by ViettelStore on 1/3/2021.
// Copyright © 2021-2022 Philip Studio. All rights reserved.
//
*/

public class EmojiEvent {
    private String emoji;

    public EmojiEvent(String emoji) {
        this.emoji = emoji;
    }

    public String getEmoji() {
        return emoji;
    }
}

