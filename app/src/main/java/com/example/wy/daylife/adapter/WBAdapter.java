package com.example.wy.daylife.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wy.daylife.R;
import com.example.wy.daylife.activity.MainActivity;
import com.example.wy.daylife.costumview.CircleImageView;
import com.example.wy.daylife.costumview.ImgContainer;
import com.example.wy.daylife.tools.ImageLoaderTool;
import com.example.wy.daylife.tools.RegxTool;
import com.example.wy.daylife.tools.ScreenUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.sina.weibo.sdk.openapi.models.Status;

import java.util.ArrayList;

import cn.lankton.flowlayout.FlowLayout;

/**
 * Created by wy on 2016/10/6.
 */
public class WBAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Status> statuses;
    private LayoutInflater inflater;

//    private ImageLoaderConfiguration config;
    private ImageLoader loader;
    private int width;

    public WBAdapter(Context context, ArrayList<Status> wb_statuses) {
        this.context=context;
        this.statuses=wb_statuses;
        inflater=LayoutInflater.from(context);

 ///       config= new ImageLoaderConfiguration.Builder(context).build();
        loader= ImageLoaderTool.getInstance(context);
//        loader.init(config);
        width= ScreenUtil.getScreenW(context);

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
            viewHolder.wb_zf_text= (TextView) convertView.findViewById(R.id.wb_zf_content_text);
            viewHolder.wb_zf_ll= (LinearLayout) convertView.findViewById(R.id.wb_zf);
            convertView.setTag(viewHolder);

        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }

        Status status=statuses.get(position);
        viewHolder.wb_face.setTag(status.user.avatar_hd);
        if(viewHolder.wb_face.getTag()!=null && viewHolder.wb_face.getTag().equals(status.user.avatar_hd)) {
            //设置布局中控件要显示的视图
            loader.displayImage(status.user.avatar_hd, viewHolder.wb_face);
        }
        viewHolder.wb_name.setText(status.user.screen_name);

        String source= RegxTool.getWBSource(status.source);
        String date=RegxTool.getDate(status.created_at);
        viewHolder.wb_source.setText(date+"     来自："+source);
        viewHolder.wb_content.setText(status.text);
        viewHolder.wb_content_img.setTag(position);

//        if(status.retweeted_status!=null){
//            viewHolder.wb_zf_ll.setVisibility(View.VISIBLE);
//            viewHolder.wb_zf_text.setText(status.retweeted_status.text);
//        }else {
            if (status.pic_urls != null) {
                if (status.pic_urls.size() > 0) {

                    if(viewHolder.wb_content_img.getTag()!=null && (int)viewHolder.wb_content_img.getTag()==position) {
                        viewHolder.wb_content_img.setVisibility(View.VISIBLE);
//                    for (int i = 0; i < status.pic_urls.size(); i++) {
//                        ImageView imageView=new ImageView(context);
//                        imageView.setTag(status.pic_urls.get(i));
//                        imageView.setImageResource(R.drawable.wb_background);
//                        if (imageView.getTag() != null && imageView.getTag().equals(status.pic_urls.get(i))) {
//                            loader.displayImage(status.pic_urls.get(i),imageView);
//                        }
////                        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//                        viewHolder.wb_content_img.addView(imageView);

//                    }
                        viewHolder.wb_content_img.setPictures(status.pic_urls);
                    }
                }
            }else{
                if(viewHolder.wb_content_img.getTag()!=null && (int)viewHolder.wb_content_img.getTag()==position) {
                    viewHolder.wb_content_img.setVisibility(View.GONE);
                }
            }
//        }

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
        public TextView wb_zf_text;
        public LinearLayout wb_zf_ll;

    }
}