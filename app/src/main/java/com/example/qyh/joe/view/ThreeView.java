package com.example.qyh.joe.view;

import com.example.qyh.joe.bean.ThreeDataBean;

import java.util.List;

/**
 * Created by admin on 2016/8/12.
 */
public interface ThreeView {
    void showImage(List<ThreeDataBean> list);

    void showFailure(Exception e, String s);

    void showProgress();

    void hideProgress();
}
