package com.example.qyh.joe.model;

/**
 * Created by admin on 2016/8/5.
 */
public interface FirstModel {
    void loadData(String url, int type, FirstModelImpl.OnLoadFirstDataListener listener);

    void loadDetailData(String url, FirstModelImpl.OnLoadFirstDataDetailListener listener);
}
