package com.example.wy.daylife.activity;

import android.os.Bundle;

import com.example.wy.daylife.R;
import com.example.wy.daylife.base.BaseActivity;
import com.example.wy.daylife.base.BaseFragment;
import com.example.wy.daylife.fragment.StatusFragment;

public class StatusDetailActivity extends BaseActivity {

    private BaseFragment fragment;
    private int position;

    @Override
    public int getLayoutId() {
        return R.layout.activity_status_detail;
    }

    @Override
    public void initData() {
        position=getIntent().getIntExtra("status",0);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initView() {
        fragment = new StatusFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("status",position);
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.status_detail_container, fragment)
                .commit();
    }

    @Override
    public void initTheme() {

    }
}
