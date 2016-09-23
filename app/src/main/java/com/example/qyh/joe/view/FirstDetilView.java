package com.example.qyh.joe.view;

/**
 * Created by admin on 2016/8/9.
 */
public interface FirstDetilView {
    void showDetilContent(String  s);
    void showProgress();
    void hideprogress();
    void showFailure(Exception e,String s);
}
