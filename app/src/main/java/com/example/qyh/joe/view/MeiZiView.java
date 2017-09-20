package com.example.qyh.joe.view;

import java.util.List;

/**
 * Created by admin on 2016/8/16.
 */
public interface MeiZiView {

    void showImage(List list);

    void showFailure(Exception e, String s);

    void showProgress();

    void hideProgress();
}
