package com.philip.studio.videoeditor.model;

public class Folder {
    private String name;
    private int size;
    private String path;
    private int image;

    public Folder(String name, int size, String path) {
        this.name = name;
        this.size = size;
        this.path = path;
    }

    public Folder(String name, int size, int image) {
        this.name = name;
        this.size = size;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public String getPath() {
        return path;
    }

    public int getImage() {
        return image;
    }
}
