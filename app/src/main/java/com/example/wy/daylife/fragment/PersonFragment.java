package com.example.wy.daylife.fragment;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.example.wy.daylife.R;
import com.example.wy.daylife.activity.MainActivity;
import com.example.wy.daylife.adapter.PersonPagerAdapter;
import com.example.wy.daylife.base.BaseFragment;
import com.sina.weibo.sdk.openapi.models.User;

import butterknife.Bind;

/**
 * Created by wy on 2016/10/5.
 */

public class PersonFragment extends BaseFragment {

    private User mUser;
    @Bind(R.id.person_tabs)
    public PagerSlidingTabStrip mStrip;
    @Bind(R.id.person_viewpager)
    public ViewPager mPager;

    public PersonFragment(){
        mUser= MainActivity.userInfo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_person,container,false);
        mPager.setAdapter(new PersonPagerAdapter(getActivity().getSupportFragmentManager()));
        mStrip.setViewPager(mPager);
        return view;
    }

}
