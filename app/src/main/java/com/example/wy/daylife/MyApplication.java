package com.example.wy.daylife;

import android.app.Application;

import com.example.wy.daylife.Interface.Constants;


/**
 * Created by wy on 2016/10/4.
 */

public class MyApplication extends Application {

    private static MyApplication mContext;

    public static boolean homeChecked=true;
    public static boolean notificationChecked=false;
    public static boolean searchChecked=false;
    public static boolean personalChecked=false;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
    }

    public static MyApplication getmContext() {
        return mContext;
    }

    public static void changeState(int checkedID){
        if(checkedID== Constants.HOME){
            homeChecked=true;
            notificationChecked=false;
            searchChecked=false;
            personalChecked=false;
        }else if(checkedID==Constants.NOTI){
            homeChecked=false;
            notificationChecked=true;
            searchChecked=false;
            personalChecked=false;
        }else if (checkedID==Constants.SEARCH){
            homeChecked=false;
            notificationChecked=false;
            searchChecked=true;
            personalChecked=false;
        }else if (checkedID==Constants.PERSONAL){
            homeChecked=false;
            notificationChecked=false;
            searchChecked=false;
            personalChecked=true;
        }
    }
}
