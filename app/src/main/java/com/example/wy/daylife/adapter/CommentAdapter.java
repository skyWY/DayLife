package com.example.wy.daylife.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.sina.weibo.sdk.openapi.models.Comment;

import java.util.List;

/**
 * Created by wy on 2016/10/31.
 */

public class CommentAdapter extends BaseAdapter {

    private List<Comment> list;
    private Context context;
    private LayoutInflater inflater;

    public CommentAdapter(Context context,List<Comment> list){
        this.list=list;
        this.context=context;
        inflater=inflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
