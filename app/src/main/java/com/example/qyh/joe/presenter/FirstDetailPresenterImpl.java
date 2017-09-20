package com.example.qyh.joe.presenter;

import android.content.Context;

import com.example.qyh.joe.bean.DataDetailBean;
import com.example.qyh.joe.model.FirstModelImpl;
import com.example.qyh.joe.model.FirstModel;
import com.example.qyh.joe.view.FirstDetailView;

/**
 * 新闻详情presenterImpl
 * Created by admin on 2016/8/9.
 */
public class FirstDetailPresenterImpl implements FirstDetailPresenter, FirstModelImpl.OnLoadFirstDataDetailListener {
    private Context context;
    private FirstDetailView firstDetailView;
    private FirstModel firstModel;

    public FirstDetailPresenterImpl(Context context, FirstDetailView firstDetailView) {
        this.context = context;
        this.firstDetailView = firstDetailView;
        firstModel = new FirstModelImpl();
    }

    @Override
    public void loadContent(String s) {
        firstDetailView.showProgress();
        firstModel.loadDetailData(s, this);
    }

    @Override
    public void onSuccess(DataDetailBean list) {
        if (list != null) {
            firstDetailView.showDetailContent(list.getBody());
        }
        firstDetailView.hideProgress();
    }

    @Override
    public void onFailure(String str, Exception e) {
        firstDetailView.hideProgress();
        firstDetailView.showFailure(e, str);
    }
}
