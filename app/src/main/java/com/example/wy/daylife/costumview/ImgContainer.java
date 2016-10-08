package com.example.wy.daylife.costumview;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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

public class ImgContainer extends ViewGroup{

    private ImageLoaderConfiguration config;
    private ImageLoader loader;
    private ArrayList<Bitmap> bitmaps;
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
        config= new ImageLoaderConfiguration.Builder(context).build();
        loader=ImageLoader.getInstance();
        loader.init(config);
        width= ScreenUtil.getScreenW(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {


        l=0;t=0;
        for(int i=0;i<getChildCount();i++){
            View child=getChildAt(i);
            int cWidth = child.getMeasuredWidth();
            int cHeight = child.getMeasuredHeight();

            if(i%3==0){
                childHeight1+=cHeight;
            }
            child.layout(l,t,l+cWidth,t+cHeight);
            l+=cWidth;
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
            setMeasuredDimension(widthSize, childHeight1);
        }

    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs)
    {
        return new MarginLayoutParams(getContext(), attrs);
    }

    public void setPics(ArrayList<String> pic_urls, Context context) {

        for(int i=0;i<pic_urls.size();i++){
            ImageView imageView=new ImageView(context);

            loader.displayImage(pic_urls.get(i),imageView);
            imageView.setLayoutParams(new LayoutParams(width/3,width/3));

            this.addView(imageView);
        }
        invalidate();
    }
}
