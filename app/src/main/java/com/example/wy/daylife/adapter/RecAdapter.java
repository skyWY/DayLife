package com.example.wy.daylife.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.wy.daylife.R;
import com.example.wy.daylife.tools.ImageLoaderTool;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wy on 2017/3/14.
 */

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.RecViewHolder> {

    private List<String> data=new ArrayList<>();
    private ImageLoader mImageLoader;
    private LayoutInflater mInflater;
    private OnItemActionListener mOnItemActionListener;

    public RecAdapter(Context context,List<String> datalist){
        data=datalist;
        mImageLoader= ImageLoaderTool.getInstance(context);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =  mInflater.inflate(R.layout.rec_item, parent,false);
        RecViewHolder simpleViewHolder = new RecViewHolder(v);
        simpleViewHolder.setIsRecyclable(true);

        return simpleViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecViewHolder holder, int position) {
        holder.setData(data.get(position));
        if(mOnItemActionListener!=null) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //注意这里必须使用viewHolder.getPosition()而不能用i，因为为了保证动画，没有使用NotifyDatasetChanged更新位置数据
                    mOnItemActionListener.onItemClickListener(v,holder.getPosition());
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //注意这里必须使用viewHolder.getPosition()而不能用i，因为为了保证动画，没有使用NotifyDatasetChanged更新位置数据
                    return mOnItemActionListener.onItemLongClickListener(v, holder.getPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class RecViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivImage;
        public RecViewHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.item_img);
        }

        void setData(Object data) {
            if (data != null) {
                String uri = (String) data;
                ivImage.setTag(uri);
                if(ivImage.getTag().equals(uri))
                    mImageLoader.displayImage(uri,ivImage);
            }
        }
    }

    /**********定义点击事件**********/
    public   interface OnItemActionListener {
        void onItemClickListener(View v,int pos);
        boolean onItemLongClickListener(View v,int pos);
    }
    public void setOnItemActionListener(OnItemActionListener onItemActionListener) {
        this.mOnItemActionListener = onItemActionListener;
    }
}
