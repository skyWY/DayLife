package com.example.wy.daylife.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wy.daylife.R;
import com.example.wy.daylife.costumview.CircleImageView;
import com.example.wy.daylife.costumview.ImgContainer;
import com.example.wy.daylife.costumview.TextViewFixTouchConsume;
import com.example.wy.daylife.tools.ImageLoaderTool;
import com.example.wy.daylife.tools.RegxTool;
import com.example.wy.daylife.tools.StringUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sina.weibo.sdk.openapi.models.Comment;

import java.util.List;

/**
 * Created by wy on 2016/10/31.
 */

public class CommentAdapter extends BaseAdapter {

    private List<Comment> list;
    private Context context;
    private LayoutInflater inflater;
    private ImageLoader loader;
    private static int emojiSize=0;

    public CommentAdapter(Context context,List<Comment> list){
        this.list=list;
        this.context=context;
        inflater=inflater.from(context);
        loader= ImageLoaderTool.getInstance(context);
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

        CommentAdapter.ViewHolder viewHolder=null;

        if(convertView==null){
            viewHolder=new CommentAdapter.ViewHolder();
            //通过LayoutInflater实例化布局
            convertView=inflater.inflate(R.layout.comment_item,null);
            viewHolder.comment_face= (CircleImageView) convertView.findViewById(R.id.comment_face);
            viewHolder.comment_name=(TextView)convertView.findViewById(R.id.comment_name);
            viewHolder.comment_source= (TextView) convertView.findViewById(R.id.comment_source);
            viewHolder.comment_content= (TextViewFixTouchConsume) convertView.findViewById(R.id.comment_content_text);

            convertView.setTag(viewHolder);

        }else {
            viewHolder=(CommentAdapter.ViewHolder)convertView.getTag();
        }

        if(emojiSize==0){
            emojiSize=(int)viewHolder.comment_content.getTextSize();
        }

        Comment comment=list.get(position);
        viewHolder.comment_face.setTag(comment.user.avatar_hd);
        if(viewHolder.comment_face.getTag()!=null && viewHolder.comment_face.getTag().equals(comment.user.avatar_hd)) {
            //设置布局中控件要显示的视图
            loader.displayImage(comment.user.avatar_hd, viewHolder.comment_face,ImageLoaderTool.getOptions());
        }
        viewHolder.comment_name.setText(comment.user.screen_name);
        String source= RegxTool.getWBSource(comment.source);
        String date=RegxTool.getDate(comment.created_at);
        viewHolder.comment_source.setText(date+"     来自："+source);

        viewHolder.comment_content.setText(StringUtils.getEmotionContent(context,emojiSize,comment.text));
        StringUtils.extractMention2Link(viewHolder.comment_content);
        return convertView;
    }

    public final class ViewHolder{

        public CircleImageView comment_face;
        public TextView comment_name;
        public TextView comment_source;
        public TextViewFixTouchConsume comment_content;
        public ImageView comment_more;
    }
}
