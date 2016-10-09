package com.example.wy.daylife.costumview;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.wy.daylife.activity.MainActivity;
import com.example.wy.daylife.tools.ScreenUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by wy on 2016/10/7.
 */

public class ImgContainer extends GridLayout{

    private int width;
    private int childHeight1=0;

    public ImgContainer(Context context) {
        this(context,null);
    }

    public ImgContainer(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ImgContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        width= ScreenUtil.getScreenW(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        l=0;t=0;
        int w=getMeasuredWidth()/getColumnCount();
        for(int i=0;i<getChildCount();i++){
            View child=getChildAt(i);
            child.layout(l,t,l+w,t+w);
            if(i>0 && i%getColumnCount()==0){
                t+=w;
            }
            l+=w;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        //开始处理wrap_content,如果一个子元素都没有，就设置为0
        if (getChildCount() == 0) {
            setMeasuredDimension(0,0);
        } else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            //ViewGroup，宽，高都是wrap_content，根据我们的需求，宽度是子控件的宽度，高度则是所有子控件的总和
            View childOne = getChildAt(0);
            int childWidth = childOne.getMeasuredWidth();
            int childHeight = childOne.getMeasuredHeight();
            setMeasuredDimension(childWidth, childHeight * getChildCount());
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //ViewGroup的宽度为wrap_content,则高度不需要管，宽度还是自控件的宽度
            View childOne = getChildAt(0);
            int childWidth = childOne.getMeasuredWidth();
            setMeasuredDimension(childWidth,heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {

            for(int i=0;i<getChildCount();i++) {
                View child = getChildAt(i);
                childHeight1+=child.getMeasuredHeight();
            }
            setMeasuredDimension(widthSize,childHeight1);
        }
    }
}
