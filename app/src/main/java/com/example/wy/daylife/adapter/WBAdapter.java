package com.example.wy.daylife.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wy.daylife.R;
import com.example.wy.daylife.activity.MainActivity;
import com.example.wy.daylife.costumview.CircleImageView;
import com.example.wy.daylife.costumview.ImgContainer;
import com.example.wy.daylife.tools.RegxTool;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.sina.weibo.sdk.openapi.models.Status;

import java.util.ArrayList;

/**
 * Created by wy on 2016/10/6.
 */
public class WBAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Status> statuses;
    private LayoutInflater inflater;

    private ImageLoaderConfiguration config;
    private ImageLoader loader;

    public WBAdapter(Context context, ArrayList<Status> wb_statuses) {
        this.context=context;
        this.statuses=wb_statuses;
        inflater=LayoutInflater.from(context);

        config= new ImageLoaderConfiguration.Builder(context).build();
        loader=ImageLoader.getInstance();
        loader.init(config);

    }

    @Override
    public int getCount() {
        return statuses.size();
    }

    @Override
    public Object getItem(int position) {
        return statuses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder=null;

        if(convertView==null){
            viewHolder=new ViewHolder();
            //通过LayoutInflater实例化布局
            convertView=inflater.inflate(R.layout.wb_item,null);
            viewHolder.wb_face= (CircleImageView) convertView.findViewById(R.id.wb_face);
            viewHolder.wb_name=(TextView)convertView.findViewById(R.id.wb_name);
            viewHolder.wb_source= (TextView) convertView.findViewById(R.id.wb_source);
            viewHolder.wb_content= (TextView) convertView.findViewById(R.id.wb_content_text);
            viewHolder.wb_zan= (TextView) convertView.findViewById(R.id.wb_zan_text);
            viewHolder.wb_zf= (TextView) convertView.findViewById(R.id.wb_zf_text);
            viewHolder.wb_comment= (TextView) convertView.findViewById(R.id.wb_comment_text);
            viewHolder.wb_content_img= (ImgContainer) convertView.findViewById(R.id.wb_content_img);
            convertView.setTag(viewHolder);


        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }

        Status status=statuses.get(position);
        //设置布局中控件要显示的视图
        loader.displayImage(status.user.avatar_hd,viewHolder.wb_face);
        viewHolder.wb_name.setText(status.user.screen_name);

        String source= RegxTool.getWBSource(status.source);
        String date=RegxTool.getDate(status.created_at);
        viewHolder.wb_source.setText(date+"     来自："+source);
        viewHolder.wb_content.setText(status.text);

//        if(status.thumbnail_pic!=null && !status.thumbnail_pic.equals("")){
//            viewHolder.wb_content_img.setVisibility(View.VISIBLE);
//            loader.displayImage(status.thumbnail_pic,viewHolder.wb_content_img);
//        }
        if(status.pic_urls!=null) {
            if (status.pic_urls.size() > 0) {
                viewHolder.wb_content_img.setVisibility(View.VISIBLE);
                viewHolder.wb_content_img.setPics(status.pic_urls,context);
            }
        }

        viewHolder.wb_zan.setText(status.attitudes_count+"");
        viewHolder.wb_zf.setText(status.reposts_count+"");
        viewHolder.wb_comment.setText(status.comments_count+"");

        return convertView;
    }

    public final class ViewHolder{

        public CircleImageView wb_face;
        public TextView wb_name;
        public TextView wb_source;
        public TextView wb_content;
        public ImgContainer wb_content_img;
        public TextView wb_zan;
        public TextView wb_comment;
        public TextView wb_zf;

    }
}
