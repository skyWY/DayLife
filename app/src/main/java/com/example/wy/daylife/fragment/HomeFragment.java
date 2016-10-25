package com.example.wy.daylife.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.wy.daylife.R;
import com.example.wy.daylife.adapter.WBAdapter;
import com.example.wy.daylife.base.BaseFragment;
import com.example.wy.daylife.tools.StatusTool;
import com.example.wy.daylife.tools.StatusWriterTool;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.StatusList;

import java.util.ArrayList;

/**
 * Created by wy on 2016/10/5.
 */

public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{

    private ListView wb_content;
    private ArrayList<Status> wb_statuses;
    private SwipeRefreshLayout refreshLayout;

    private static final int REFRESH_COMPLETE = 0X110;
    private Adapter wbAdapter;

    public HomeFragment(){
        wb_statuses=new ArrayList<>();
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
        refreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.home_fresh);
        refreshLayout.setOnRefreshListener(this);
        String status= StatusWriterTool.readStatus();

        if(status!=null && !status.equals(""))
            wb_statuses= StatusList.parse(status).statusList;

        if(wb_statuses.size()>0){
            wbAdapter=new WBAdapter(getActivity(), wb_statuses);
            wb_content.setAdapter((ListAdapter) wbAdapter);
        }else {
            new StatusTool(getActivity()).getfriendsTimeline(new StatusTool.StatusCallBack() {
                @Override
                public void getStatus(ArrayList<Status> statuses) {
                    wb_statuses = statuses;
                    wbAdapter=new WBAdapter(getActivity(), wb_statuses);
                    wb_content.setAdapter((ListAdapter) wbAdapter);
                }
            });
        }
    }

    @Override
    public void onRefresh() {

        new StatusTool(getActivity()).getfriendsTimeline(new StatusTool.StatusCallBack() {
            @Override
            public void getStatus(ArrayList<Status> statuses) {
                wb_statuses = statuses;
                wbAdapter=new WBAdapter(getActivity(), wb_statuses);
                wb_content.setAdapter((ListAdapter) wbAdapter);
            }
        });
        mHandler.sendEmptyMessageDelayed(REFRESH_COMPLETE, 2000);
    }

    private Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case REFRESH_COMPLETE:
                    synchronized (wbAdapter) {
                        wbAdapter.notifyAll();
                    }
                    refreshLayout.setRefreshing(false);
                    break;

            }
        };
    };
}
