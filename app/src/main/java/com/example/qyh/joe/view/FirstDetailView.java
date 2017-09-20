package com.example.qyh.joe.view;

/**
 * Created by admin on 2016/8/9.
 */
public interface FirstDetailView {
    void showDetailContent(String s);

    void showProgress();

    void hideProgress();

    void showFailure(Exception e, String s);
}
