package com.example.qyh.joe.presenter;

import com.example.qyh.joe.bean.ThreeDataBean;
import com.example.qyh.joe.model.ThreeModel;
import com.example.qyh.joe.model.ThreeModelImpl;
import com.example.qyh.joe.view.ThreeView;

import java.util.List;

/**
 * Created by admin on 2016/8/12.
 */
public class ThreePresenterImpl implements ThreePresenter, ThreeModelImpl.onLoanImageListener {
    private ThreeView threeView;
    private ThreeModel threeModel;

    public ThreePresenterImpl(ThreeView threeview) {
        this.threeView = threeview;
        this.threeModel = new ThreeModelImpl();
    }

    @Override
    public void loadImageList() {
        System.out.println("loadImageList==================");
        threeView.showProgress();
        threeModel.onLoadImage(this);
    }

    @Override
    public void onSuccess(List<ThreeDataBean> list) {
        System.out.println("onSuccess=========");
        if (null != list) {
            threeView.showImage(list);
            threeView.hideProgress();
        }
    }

    @Override
    public void onFailure(Exception e, String s) {
        threeView.hideProgress();
        threeView.showFailure(e, s);
    }
}
