package com.example.qyh.joe.theme.base;

import android.app.Application;

import com.example.qyh.joe.theme.SkinManager;


/**
 * Created by admin on 2016/10/27.
 */

public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.getInstance().init(this);
    }
}
