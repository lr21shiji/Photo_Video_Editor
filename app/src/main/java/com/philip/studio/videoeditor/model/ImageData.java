package com.philip.studio.videoeditor.model;

public class ImageData {
    private String imageData;
    private boolean isClick;

    public ImageData(String imageData, boolean isClick) {
        this.imageData = imageData;
        this.isClick = isClick;
    }

    public String getImageData() {
        return imageData;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public void setClick(boolean click) {
        isClick = click;
    }
}
