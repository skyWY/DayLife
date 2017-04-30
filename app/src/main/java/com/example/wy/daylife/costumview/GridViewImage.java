package com.example.wy.daylife.costumview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.wy.daylife.R;
import com.example.wy.daylife.activity.ImageShowActivity;
import com.example.wy.daylife.tools.ImageLoaderTool;
import com.example.wy.daylife.tools.ScreenUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by wy on 2016/10/7.
 */

public class GridViewImage extends LinearLayout{

    private int width;
    private int gap=5;

    private Context context;
    private int picWidth;
    private int left;
    private int top;
    private int right;
    private int bottom;


    public GridViewImage(Context context) {
        this(context,null);
    }

    public GridViewImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        width= ScreenUtil.getScreenW(context);
        picWidth=(width-10)/3;
        this.context=context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int childCount=getChildCount();
        int row=0;
        int height=0;

        if(childCount==0) row=0;
        else if(childCount<=3) row=1;
        else if(childCount<=6) row=2;
        else row=3;

        if(row>0)
            height = picWidth * row + gap * (row - 1);

        // 计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(width,height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int childCount=getChildCount();
        int row=0;

        if(childCount==0) row=0;
        else if(childCount<=3) row=1;
        else if(childCount<=6) row=2;
        else row=3;

        //根据子view数量确定高度
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = picWidth * row + gap * (row - 1);
        setLayoutParams(params);

        Log.i("GridVIew",this.getMeasuredHeight()+"");

        left=0;
        top=0;
        right=left+picWidth;
        bottom=top+picWidth;

        for(int i=0;i<childCount;i++){
            ImageView view= (ImageView) getChildAt(i);
            view.setVisibility(View.VISIBLE);

            view.layout(left,top,right,bottom);

            if(i!=2 && i!=5) {
                left = right + gap;
                right = left + picWidth;
            }else{
                top=top+picWidth+gap;
                bottom=top+picWidth;
                left=0;
                right=left+picWidth;
            }

        }
    }

}
