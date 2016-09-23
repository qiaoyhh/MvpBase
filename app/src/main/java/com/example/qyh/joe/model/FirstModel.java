package com.example.qyh.joe.model;

/**
 * Created by admin on 2016/8/5.
 */
public interface FirstModel {
    void loadData(String url,int type,FirstModeImpl.OnLoadFirstDataListener listener);
    void loadDetilData(String url,FirstModeImpl.OnloadFirstDataDetilListener listener);
}
