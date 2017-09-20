package com.example.qyh.joe.model;

import com.example.qyh.joe.bean.ThreeDataBean;
import com.example.qyh.joe.commons.Urls;
import com.example.qyh.joe.utils.ImageJsonUtils;
import com.example.qyh.joe.utils.OkHttpUtils;

import java.util.List;

/**
 * Created by admin on 2016/8/12.
 */
public class ThreeModelImpl implements ThreeModel {
    @Override
    public void onLoadImage(final onLoanImageListener listener) {
        String url = Urls.IMAGES_URL;
        OkHttpUtils.ResultCallback<String> loadNewsCallback = new OkHttpUtils.ResultCallback<String>() {
            @Override
            public void onSuccess(String response) {
                List<ThreeDataBean> threeDataBeanList = ImageJsonUtils.readJsonThreeDataBeans(response);
                listener.onSuccess(threeDataBeanList);
            }

            @Override
            public void onFailure(Exception e) {
                listener.onFailure(e, "load image list failure");
            }
        };
        OkHttpUtils.get(url, loadNewsCallback);
    }

    public interface onLoanImageListener {
        void onSuccess(List<ThreeDataBean> list);

        void onFailure(Exception e, String s);
    }
}
