package com.example.wy.daylife.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wy.daylife.R;
import com.sina.weibo.sdk.openapi.models.Group;

import java.util.List;

/**
 * Created by wy on 2016/10/5.
 */
public class GroupListAdapter extends BaseAdapter {

    private Context context;
    private List<Group> groups;
    private LayoutInflater inflater;

    public GroupListAdapter(Context context, List<Group> groups) {
        this.context = context;
        this.groups = groups;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return groups.size();
    }

    @Override
    public Object getItem(int position) {
        return groups.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
//通过LayoutInflater实例化布局
            convertView = inflater.inflate(R.layout.group_list_item, null);

            viewHolder.groupName = (TextView) convertView.findViewById(R.id.group_list_item);
            convertView.setTag(viewHolder);
        } else {
//通过tag找到缓存的布局
            viewHolder = (ViewHolder) convertView.getTag();
        }
//设置布局中控件要显示的视图

        viewHolder.groupName.setText(groups.get(position).name);

        return convertView;
    }

    public final class ViewHolder {
        public TextView groupName;
    }
}
