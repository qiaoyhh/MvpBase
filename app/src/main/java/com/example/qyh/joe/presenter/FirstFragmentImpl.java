package com.example.qyh.joe.presenter;

import com.example.qyh.joe.bean.DataBean;
import com.example.qyh.joe.commons.Urls;
import com.example.qyh.joe.fragment.FirstFragment;
import com.example.qyh.joe.model.FirstModelImpl;
import com.example.qyh.joe.model.FirstModel;
import com.example.qyh.joe.view.FirstView;

import java.util.List;

/**
 * Created by qyh on 2016/8/5.
 */
public class FirstFragmentImpl implements FirstPresenter, FirstModelImpl.OnLoadFirstDataListener {
    public FirstView firstView;
    public FirstModel firstModel;

    public FirstFragmentImpl(FirstView view) {
        this.firstView = view;
        this.firstModel = new FirstModelImpl();
    }

    @Override
    public void loadData(Object type, int page) {
        String url = getUrl((Integer) type, page);
        System.out.println("url=========" + url);
        if (page == 0) {
            firstView.showProgress();
        }
        firstModel.loadData(url, (Integer) type, this);
    }

    private String getUrl(int type, int page) {
        StringBuilder sb = new StringBuilder();
        switch (type) {
            case FirstFragment.ONE:
                sb.append(Urls.TOP_URL).append(Urls.TOP_ID);
                break;
            case FirstFragment.TWO:
                sb.append(Urls.COMMON_URL).append(Urls.NBA_ID);
                break;
            case FirstFragment.THREE:
                sb.append(Urls.COMMON_URL).append(Urls.CAR_ID);
                break;
            case FirstFragment.FOUR:
                sb.append(Urls.COMMON_URL).append(Urls.JOKE_ID);
                break;
            default:
                sb.append(Urls.TOP_URL).append(Urls.TOP_ID);
                break;
        }
        sb.append("/").append(page).append(Urls.END_URL);
        return sb.toString();
    }

    @Override
    public void onSuccess(List<DataBean> list) {
        firstView.hideProgress();
        firstView.addData(list);
    }

    @Override
    public void onFailure(String str, Exception e) {
        firstView.hideProgress();
        firstView.showLoadFail();
    }
}
