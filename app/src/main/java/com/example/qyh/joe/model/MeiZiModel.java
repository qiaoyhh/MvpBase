package com.example.qyh.joe.model;

/**
 * Created by admin on 2016/8/16.
 */
public interface MeiZiModel {
    void loadPicture(String url, String type, MeiZiModelImpl.onLoadPictureListener listener);
}
