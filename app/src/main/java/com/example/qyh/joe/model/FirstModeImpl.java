package com.example.qyh.joe.model;

import com.example.qyh.joe.fragment.FirstFragment;
import com.example.qyh.joe.bean.DataBean;
import com.example.qyh.joe.commons.Urls;
import com.example.qyh.joe.utils.NewsJsonUtils;
import com.example.qyh.joe.utils.OkHttpUtils;
import com.example.qyh.joe.bean.DataDetilBean;

import java.util.List;

/**
 * Created by qyh on 2016/8/8.
 */
public class FirstModeImpl implements FirstModel {
    //加载首页数据条目相关
    @Override
    public void loadData(final String url, final int type, final OnLoadFirstDataListener listener) {
        OkHttpUtils.ResultCallback<String> loadNewsCallback=new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                List<DataBean> dataBeans = NewsJsonUtils.readJsonDataBeans(response, getID(type));
                listener.onSuccess(dataBeans);
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("load news list failure.",e);
            }
        };
        OkHttpUtils.get(url, loadNewsCallback);
    }
    //加载新闻详情数据
    @Override
    public void loadDetilData(final String dcoid,  final OnloadFirstDataDetilListener listener) {
         String detailUrl = getDetailUrl(dcoid);
        OkHttpUtils.ResultCallback<String> loadNewsCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                DataDetilBean newsDetailBean = NewsJsonUtils.readJsonNewsDetailBeans(response, dcoid);
                listener.onSuccess(newsDetailBean);
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure("load news detail info failure.", e);
            }
        };
        OkHttpUtils.get(detailUrl, loadNewsCallback);
    }
    public interface OnLoadFirstDataListener{
        void  onSuccess(List<DataBean> list);
        void  onFailure(String str,Exception e);
    }
    public interface OnloadFirstDataDetilListener{
        void onSuccess(DataDetilBean list);
        void onFailure(String str,Exception e);
    }
    /**
     * 获取ID
     * @param type
     * @return
     */
    private String getID(int type) {
        String id;
        switch (type) {
            case FirstFragment.ONE:
                id = Urls.TOP_ID;
                break;
            case FirstFragment.TWO:
                id = Urls.NBA_ID;
                break;
            case FirstFragment.THREE:
                id = Urls.CAR_ID;
                break;
            case FirstFragment.FOUR:
                id = Urls.JOKE_ID;
                break;
            default:
                id = Urls.TOP_ID;
                break;
        }
        return id;
    }
    private String getDetailUrl(String docId) {
        StringBuffer sb = new StringBuffer(Urls.NEW_DETAIL);
        sb.append(docId).append(Urls.END_DETAIL_URL);
        return sb.toString();
    }
}
