package com.example.qyh.joe.presenter;

import android.content.Context;

import com.example.qyh.joe.model.FirstModeImpl;
import com.example.qyh.joe.model.FirstModel;
import com.example.qyh.joe.view.FirstDetilView;
import com.example.qyh.joe.bean.DataDetilBean;

/**
 * 新闻详情presenterImpl
 * Created by admin on 2016/8/9.
 */
public class FirstDetilPresenterImpl implements FirstDetilPresenter,FirstModeImpl.OnloadFirstDataDetilListener {
    private Context mcontext;
    private FirstDetilView mfirstDetilView;
    private FirstModel mFirstModel;

   public  FirstDetilPresenterImpl(Context context, FirstDetilView firstDetilView){
        this.mcontext=context;
       this.mfirstDetilView=firstDetilView;
       mFirstModel=new FirstModeImpl();
    }
    @Override
    public void loadContent(String s) {
        mfirstDetilView.showProgress();
        mFirstModel.loadDetilData(s,this);
    }

    @Override
    public void onSuccess(DataDetilBean list) {
        if(list!=null){
            mfirstDetilView.showDetilContent(list.getBody());
        }
        mfirstDetilView.hideprogress();
    }

    @Override
    public void onFailure(String str, Exception e) {
        mfirstDetilView.hideprogress();
        mfirstDetilView.showFailure(e,str);
    }
}
