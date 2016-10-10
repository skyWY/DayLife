package com.example.wy.daylife.costumview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.wy.daylife.activity.MainActivity;
import com.example.wy.daylife.tools.AsyncImageLoader;
import com.example.wy.daylife.tools.ScreenUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.sina.weibo.sdk.openapi.models.Status;

import java.util.ArrayList;

/**
 * Created by wy on 2016/10/7.
 */

public class ImgContainer extends ViewGroup{

    private int width;
    private ArrayList<String> status_url;
    private int gap=5;
    private int column=0;
    private int row=0;

    private AsyncImageLoader imageLoader;

    private ImageLoaderConfiguration config;
    private ImageLoader loader;

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
        imageLoader=new AsyncImageLoader(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        measureChildren(widthMeasureSpec,heightMeasureSpec);
//        //开始处理wrap_content,如果一个子元素都没有，就设置为0
//        if (getChildCount() == 0) {
//            setMeasuredDimension(0,0);
//        } else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
//            //ViewGroup，宽，高都是wrap_content，根据我们的需求，宽度是子控件的宽度，高度则是所有子控件的总和
//            View childOne = getChildAt(0);
//            int childWidth = childOne.getMeasuredWidth();
//            int childHeight = childOne.getMeasuredHeight();
//            setMeasuredDimension(childWidth, childHeight * getChildCount());
//        } else if (widthMode == MeasureSpec.AT_MOST) {
//            //ViewGroup的宽度为wrap_content,则高度不需要管，宽度还是自控件的宽度
//            View childOne = getChildAt(0);
//            int childWidth = childOne.getMeasuredWidth();
//            setMeasuredDimension(childWidth,heightSize);
//        } else if (heightMode == MeasureSpec.AT_MOST) {
//
//            for(int i=0;i<getChildCount();i++) {
//                View child = getChildAt(i);
//                childHeight1+=child.getMeasuredHeight();
//            }
//            setMeasuredDimension(widthSize,childHeight1);
//        }
    }

    public void setPictures(ArrayList<String> data){

        //初始化布局的行和列
        generateLayout(data.size());

        if(status_url==null){
            for(int i=0;i<data.size();i++){
                ImageView iv = generateImageView();
                addView(iv,generateDefaultLayoutParams());
            }
        }else{
            int oldcount=status_url.size();
            int newcount=data.size();
            if(oldcount>newcount){
                removeViews(newcount - 1, oldcount - newcount);
            }else if(oldcount<newcount){
                for(int i=0;i<newcount-oldcount;i++){
                    ImageView iv = generateImageView();
                    addView(iv,generateDefaultLayoutParams());
                }
            }
        }
        status_url=data;
        layoutChildren();
    }

    private void layoutChildren() {
        int childCount=status_url.size();
        int singleWidth = (width - gap * (3 - 1)) / 3;
        int singleHeight = singleWidth;

        //根据子view数量确定高度
        ViewGroup.LayoutParams params = getLayoutParams();
        params.height = singleHeight * row + gap * (row - 1);
        setLayoutParams(params);

        for (int i = 0; i < childCount; i++) {
            ImageView childrenView = (ImageView) getChildAt(i);
            childrenView.setTag(status_url.get(i));
//            if (childrenView.getTag() != null && childrenView.getTag().equals(status_url.get(i))) {
//                loader.displayImage(status_url.get(i),childrenView);
//            }
            imageLoader.loadImage(childrenView,status_url.get(i));

            int[] position = findPosition(i);
            int left = (singleWidth + gap) * position[1];
            int top = (singleHeight + gap) * position[0];
            int right = left + singleWidth;
            int bottom = top + singleHeight;

            childrenView.layout(left, top, right, bottom);
        }
    }

    private int[] findPosition(int childNum) {
        int[] position = new int[2];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if ((i * column + j) == childNum) {
                    position[0] = i;//行
                    position[1] = j;//列
                    break;
                }
            }
        }
        return position;
    }

    private ImageView generateImageView() {
        ImageView iv = new ImageView(getContext());
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        iv.setBackgroundColor(Color.parseColor("#f5f5f5"));
        return iv;
    }

    private void generateLayout(int size) {
        if(size<=3){
            column=size;
            row=1;
        }else if(size<=6){
            column=3;
            row=2;
        }else if(size<=9){
            column=3;
            row=3;
        }
    }
}
