package com.example.wy.daylife.fragment;

import android.app.Activity;
import android.content.Context;
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
import com.example.wy.daylife.costumview.CircleImageView;
import com.example.wy.daylife.tools.ImageLoaderTool;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sina.weibo.sdk.openapi.models.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by wy on 2016/10/5.
 */

public class PersonFragment extends BaseFragment {

    private User mUser;

    public ViewPager mPager;
    List<BaseFragment> fs;
    private CircleImageView face;
    private ImageLoader loader;
    private Activity mActivity;

    public PersonFragment(){
        mUser= MainActivity.userInfo;

    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        mActivity=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_person,container,false);
        initView(view);
        initData();
        mPager.setAdapter(new PersonPagerAdapter(getChildFragmentManager(),fs));
        mPager.setCurrentItem(0);
        return view;
    }

    private void initView(View view) {
        mPager= (ViewPager) view.findViewById(R.id.person_viewpager);
        face= (CircleImageView) view.findViewById(R.id.person_face);
        loader = ImageLoaderTool.getInstance(getMactivity());
        loader.displayImage(mUser.avatar_hd,face);

    }

    private void initData() {

        fs = new ArrayList<>();
        fs.add(new HomeFragment());
        fs.add(new HomeFragment());
        fs.add(new HomeFragment());
    }

    public Context getMactivity() {
        return mActivity;
    }
}
