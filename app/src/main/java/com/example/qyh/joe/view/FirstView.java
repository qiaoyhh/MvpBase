package com.example.qyh.joe.view;

import com.example.qyh.joe.bean.DataBean;

import java.util.List;

/**
 * Created by qyh on 2016/8/5.
 */
public interface FirstView {
    void showProgress();

    void hideProgress();

    void addData(List<DataBean> dataBeanList);

    void showLoadFail();
}
