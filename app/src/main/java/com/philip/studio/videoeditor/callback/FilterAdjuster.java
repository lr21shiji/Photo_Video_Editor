package com.philip.studio.videoeditor.callback;/*
//
// Project: Video Editor
// Created by ViettelStore on 1/11/2021.
// Copyright © 2021-2022 Philip Studio. All rights reserved.
//
*/

import com.daasuu.gpuv.egl.filter.GlFilter;

public interface FilterAdjuster {
    public void adjust(GlFilter filter, int percentage);
}
