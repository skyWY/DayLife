package com.example.wy.daylife.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.wy.daylife.R;
import com.example.wy.daylife.adapter.CommentAdapter;
import com.example.wy.daylife.adapter.WBAdapter;
import com.example.wy.daylife.base.BaseFragment;
import com.example.wy.daylife.tools.CommentsTool;
import com.example.wy.daylife.tools.StatusTool;
import com.sina.weibo.sdk.openapi.models.Comment;
import com.sina.weibo.sdk.openapi.models.Status;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatusFragment extends BaseFragment {

    private ListView listView;

    private View footer;
    private ProgressBar progressBar;
    private long id;

    private List<Comment> wb_comments=new ArrayList<>();
    private CommentAdapter adapter;
    private static final int LOAD_COMPLETE=0X120;

    private static final String TAG="StatusFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id=getArguments().getLong("wb_id");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_status, container, false);
        footer=inflater.inflate(R.layout.footer_view,null);
        progressBar= (ProgressBar) footer.findViewById(R.id.listview_footer);
        initData(view);
        return view;
    }

    public void initView(){
    }

    public void initData(View view){
        listView= (ListView) view.findViewById(R.id.comment_listview);
        listView.addFooterView(footer);

        new CommentsTool(getActivity(),0,0,id).getWBComments(new CommentsTool.StatusCallBack() {
            @Override
            public void getComments(ArrayList<Comment> comments) {

                if(comments==null || comments.size()==0)
                    Toast.makeText(getActivity(),"暂无评论",Toast.LENGTH_LONG);

                wb_comments = comments;
                adapter=new CommentAdapter(getActivity(),wb_comments);
                listView.setAdapter(adapter);
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    if(view.getLastVisiblePosition() == view.getCount() - 1){
                        Comment lastComment=wb_comments.get(wb_comments.size()-1);
                        long maxid=Long.parseLong(lastComment.id)-1;
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

        progressBar.setVisibility(View.VISIBLE);
        new CommentsTool(getActivity(),0,maxid,id).getWBComments(new CommentsTool.StatusCallBack() {
            @Override
            public void getComments(ArrayList<Comment> comments) {
               if(comments!=null) wb_comments.addAll(comments);
            }
        });
        mHandler.sendEmptyMessageDelayed(LOAD_COMPLETE, 1000);
    }

    private Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case LOAD_COMPLETE:
                    progressBar.setVisibility(View.GONE);
                    synchronized (adapter) {
                        adapter.notifyDataSetChanged();
                    }
                    break;

            }
        };
    };

    public static StatusFragment newInstance(long id){
        Bundle args=new Bundle();
        args.putLong("wb_id",id);

        StatusFragment fragment=new StatusFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
