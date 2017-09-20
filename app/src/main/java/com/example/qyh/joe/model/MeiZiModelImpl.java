package com.example.qyh.joe.model;

import java.util.List;

/**
 * Created by admin on 2016/8/16.
 */
public class MeiZiModelImpl implements MeiZiModel {

    @Override
    public void loadPicture(final String url, final String type, final onLoadPictureListener listener) {
        System.out.println("loadPicture============" + url);
    }

    public interface onLoadPictureListener<E> {
        void onSuccess(List<E> list);

        void onFailure();
    }
}
