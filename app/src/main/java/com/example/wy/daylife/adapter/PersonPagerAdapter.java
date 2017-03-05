package com.example.wy.daylife.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.wy.daylife.base.BaseFragment;

import java.util.List;

/**
 * Created by wy on 2017/3/2.
 */

public class PersonPagerAdapter extends FragmentPagerAdapter {

    List<BaseFragment> fragments;
    String[] titles={"全部","收藏","相册"};

    public PersonPagerAdapter(FragmentManager fm,List<BaseFragment> fragments) {
        super(fm);
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    //设置tablayout和viewpager联动后，要通过此方法设置tab的标题，否则不显示
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
