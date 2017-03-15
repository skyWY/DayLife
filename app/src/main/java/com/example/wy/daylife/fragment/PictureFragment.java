package com.example.wy.daylife.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.wy.daylife.R;
import com.example.wy.daylife.adapter.RecAdapter;
import com.example.wy.daylife.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wy on 2017/3/14.
 */

public class PictureFragment extends BaseFragment {

    private RecyclerView mRecyclerView;
    private List<String> mDatas = null;

    private RecAdapter mRecAdapter=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_pic,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recylerview);
        return view;
    }

    private void initDataAndView() {
        mDatas = new ArrayList<String>();
        for(int i='A';i<='z';i++) {
            mDatas.add(String.valueOf((char)i));
        }
        mRecAdapter = new RecAdapter(getActivity(),mDatas);
        mRecyclerView.setAdapter(mRecAdapter);
        //设置网格布局管理器
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        mRecAdapter.setOnItemActionListener(new RecAdapter.OnItemActionListener() {
            @Override
            public void onItemClickListener(View v, int pos) {

            }

            @Override
            public boolean onItemLongClickListener(View v, int pos) {
                return false;
            }
        });

    }
}
