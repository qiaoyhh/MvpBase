package com.example.qyh.joe.model;

/**
 * Created by admin on 2016/8/11.
 */
public interface SecondModel {
    void loadData(String url, int type, SecondModeImpl.onLoadDataListener listener);
}
