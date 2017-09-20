package com.example.qyh.joe.model;

import com.example.qyh.joe.bean.SecondeDataBean;

import java.util.List;

/**
 * Created by admin on 2016/8/11.
 */
public class SecondModeImpl implements SecondModel {


    @Override
    public void loadData(String url, int type, onLoadDataListener listener) {

    }

    public interface onLoadDataListener {
        void onSuccess(List<SecondeDataBean> List);

        void onfailure(Exception e, String s);
    }
}
