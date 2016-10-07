package com.example.wy.daylife;

import android.app.Application;


/**
 * Created by wy on 2016/10/4.
 */

public class MyApplication extends Application {

    private static MyApplication mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
    }

    public static MyApplication getmContext() {
        return mContext;
    }
}
