package com.example.wy.daylife.activity;

import android.content.Intent;
import android.widget.BaseAdapter;

import com.example.wy.daylife.Interface.IWeiboActivity;
import com.example.wy.daylife.base.BaseActivity;
import com.example.wy.daylife.bean.Task;
import com.example.wy.daylife.service.MainService;

/**
 * Created by wy on 2016/10/11.
 */

public class LoginActivity extends BaseActivity implements IWeiboActivity {


    @Override
    public void init() {
    }

    @Override
    public void refresh(Object... objects) {

    }


    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initData() {
        Intent intent=new Intent(this, MainService.class);
        startService(intent);
        Task task=new Task(Task.WB_LOGIN,null);
        MainService.addTask(task);
        MainService.addActivity(this);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initTheme() {

    }
}
