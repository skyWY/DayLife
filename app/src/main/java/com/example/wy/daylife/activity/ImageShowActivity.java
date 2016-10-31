package com.example.wy.daylife.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.wy.daylife.R;
import com.example.wy.daylife.base.BaseActivity;

import java.util.List;

import butterknife.Bind;

public class ImageShowActivity extends BaseActivity {

    private List<String> list;
    @Bind(R.id.img_show_viewpager)
    private ViewPager viewPager;
    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void initData() {
        list=getIntent().getStringArrayListExtra("imgUrl");
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
