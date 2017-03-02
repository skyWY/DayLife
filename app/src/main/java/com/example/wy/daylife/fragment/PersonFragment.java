package com.example.wy.daylife.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wy.daylife.activity.MainActivity;
import com.example.wy.daylife.base.BaseFragment;
import com.sina.weibo.sdk.openapi.models.User;

/**
 * Created by wy on 2016/10/5.
 */

public class PersonFragment extends BaseFragment {

    private User mUser;

    public PersonFragment(){
        mUser= MainActivity.userInfo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
