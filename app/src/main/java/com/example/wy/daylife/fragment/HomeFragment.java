package com.example.wy.daylife.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.wy.daylife.R;
import com.example.wy.daylife.adapter.WBAdapter;
import com.example.wy.daylife.base.BaseFragment;
import com.example.wy.daylife.tools.StatusTool;
import com.sina.weibo.sdk.openapi.models.Status;

import java.util.ArrayList;

/**
 * Created by wy on 2016/10/5.
 */

public class HomeFragment extends BaseFragment {

    private ListView wb_content;
    private ArrayList<Status> wb_statuses;

    public HomeFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        initData(view);
        return view;
    }

    private void initView(View view) {

    }

    public void initData(View view){
        wb_content= (ListView) view.findViewById(R.id.home_listView);
        new StatusTool(getActivity()).getfriendsTimeline(new StatusTool.StatusCallBack() {
            @Override
            public void getStatus(ArrayList<Status> statuses) {
                wb_statuses=statuses;
                wb_content.setAdapter(new WBAdapter(getActivity(),wb_statuses));
            }
        });
    }
}
