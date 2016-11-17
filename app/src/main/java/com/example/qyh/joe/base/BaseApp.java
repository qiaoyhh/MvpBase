package com.example.qyh.joe.base;

import android.app.Application;

import com.example.qyh.joe.theme.SkinManager;

/**
 * 描述：
 * Created by qyh on 2016/10/28.
 */

public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //注册换肤
        SkinManager.getInstance().init(this);
    }
}
