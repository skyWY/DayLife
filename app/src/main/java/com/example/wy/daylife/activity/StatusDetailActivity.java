package com.example.wy.daylife.activity;

import android.os.Bundle;
import android.util.Log;

import com.example.wy.daylife.R;
import com.example.wy.daylife.base.BaseActivity;
import com.example.wy.daylife.base.BaseFragment;
import com.example.wy.daylife.fragment.StatusFragment;

public class StatusDetailActivity extends BaseActivity {

    private BaseFragment fragment;
    private String wb_id;
    private static final String TAG="StatusDetailActivity";

    @Override
    public int getLayoutId() {
        return R.layout.activity_status_detail;
    }

    @Override
    public void initData() {
        wb_id=getIntent().getStringExtra("status");
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initView() {
        long id=Long.parseLong(wb_id);
        Log.i(TAG,wb_id);
        fragment = StatusFragment.newInstance(id);
        getSupportFragmentManager().beginTransaction().replace(R.id.status_detail_container, fragment)
                .commit();
    }

    @Override
    public void initTheme() {

    }
}
