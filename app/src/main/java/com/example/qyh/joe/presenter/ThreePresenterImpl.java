package com.example.qyh.joe.presenter;

import com.example.qyh.joe.bean.ThreeDataBean;
import com.example.qyh.joe.model.ThreeModel;
import com.example.qyh.joe.model.ThreeModelImpl;
import com.example.qyh.joe.view.ThreeView;

import java.util.List;

/**
 * Created by admin on 2016/8/12.
 */
public class ThreePresenterImpl implements ThreePresenter,ThreeModelImpl.onLoanImageListener {
    private ThreeView mThreeView;
    private ThreeModel mThreeModle;

    public ThreePresenterImpl(ThreeView threeview) {
        this.mThreeView=threeview;
        this.mThreeModle=new ThreeModelImpl();
    }

    @Override
    public void loadImageList() {
        System.out.println("loadImageList==================");
        mThreeView.showProgress();
        mThreeModle.onLoadImage(this);
    }

    @Override
    public void onSuccess(List<ThreeDataBean> list) {
        System.out.println("onSuccess=========");
        if(null!=list){
            mThreeView.showImage(list);
            mThreeView.hideProgress();
        }
    }

    @Override
    public void onFailure(Exception e, String s) {
        mThreeView.hideProgress();
        mThreeView.showFailure(e,s);
    }
}
