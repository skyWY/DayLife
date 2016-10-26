package com.example.wy.daylife.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

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
    private static final int LOAD_COMPLETE=0X120;
    private BaseAdapter wbAdapter;

    private View footer;
    private ProgressBar progressBar;
    private static String TAG="HomeFragment";

    public HomeFragment(){
        wb_statuses=new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        footer=inflater.inflate(R.layout.footer_view,null);
        progressBar= (ProgressBar) footer.findViewById(R.id.listview_footer);
        initData(view);
        return view;
    }

    private void initView(View view) {

    }

    public void initData(View view){
        wb_content= (ListView) view.findViewById(R.id.home_listView);
        wb_content.addFooterView(footer);
//        progressBar= (ProgressBar) view.findViewById(R.id.listview_footer);
        refreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.home_fresh);
        refreshLayout.setOnRefreshListener(this);
        final String status= StatusWriterTool.readStatus();

        if(status!=null && !status.equals(""))
            wb_statuses= StatusList.parse(status).statusList;

        if(wb_statuses!=null && wb_statuses.size()>0){
            wbAdapter=new WBAdapter(getActivity(), wb_statuses);
            wb_content.setAdapter(wbAdapter);
        }else {
            new StatusTool(getActivity(),0,0).getfriendsTimeline(new StatusTool.StatusCallBack() {
                @Override
                public void getStatus(ArrayList<Status> statuses) {
                    wb_statuses = statuses;
                    wbAdapter=new WBAdapter(getActivity(), wb_statuses);
                    wb_content.setAdapter(wbAdapter);
                }
            });
        }

        wb_content.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    if(view.getLastVisiblePosition() == view.getCount() - 1){
                        Status lastStatus=wb_statuses.get(wb_statuses.size()-1);
                        long maxid=Long.parseLong(lastStatus.id)-1;
//                        wb_content.setEnabled(false);
                        loadData(maxid);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount1, int totalItemCount) {

            }
        });
    }

    public void loadData(long maxid){
        Log.i(TAG,""+maxid);
        progressBar.setVisibility(View.VISIBLE);
        new StatusTool(getActivity(),0,maxid).getfriendsTimeline(new StatusTool.StatusCallBack() {
            @Override
            public void getStatus(ArrayList<Status> statuses) {
                if(statuses!=null) {
                    wb_statuses.addAll(statuses);
//                    wbAdapter = new WBAdapter(getActivity(), wb_statuses);
//                    wb_content.setAdapter((ListAdapter) wbAdapter);
                }
            }
        });
        mHandler.sendEmptyMessageDelayed(LOAD_COMPLETE, 1000);
    }

    @Override
    public void onRefresh() {

        new StatusTool(getActivity(),0,0).getfriendsTimeline(new StatusTool.StatusCallBack() {
            @Override
            public void getStatus(ArrayList<Status> statuses) {
                wb_statuses = statuses;
                wbAdapter=new WBAdapter(getActivity(), wb_statuses);
                wb_content.setAdapter(wbAdapter);
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
                        wbAdapter.notifyDataSetChanged();
                    }
                    refreshLayout.setRefreshing(false);
                    break;
                case LOAD_COMPLETE:
                    progressBar.setVisibility(View.GONE);
//                    wb_content.setEnabled(true);
                    synchronized (wbAdapter) {
                        wbAdapter.notifyDataSetChanged();
//                        wb_content.setSelection(visibleLastIndex -  1);
                    }
                    break;

            }
        };
    };
}
