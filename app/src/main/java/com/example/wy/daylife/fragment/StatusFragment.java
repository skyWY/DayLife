package com.example.wy.daylife.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.wy.daylife.R;
import com.example.wy.daylife.adapter.CommentAdapter;
import com.example.wy.daylife.base.BaseFragment;
import com.sina.weibo.sdk.openapi.models.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatusFragment extends BaseFragment {

    private ListView listView;
    private List<Comment> list=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_status, container, false);
        listView= (ListView) view.findViewById(R.id.comment_listview);
        return view;
    }

    public void initView(){
        listView.setAdapter(new CommentAdapter(getActivity(),list));
    }

}
