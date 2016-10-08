package com.example.wy.daylife.costumview;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

/**
 * Created by wy on 2016/10/7.
 */

public class ImgContainer extends ViewGroup{

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
        loader=ImageLoader.getInstance();
        loader.init(config);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

//        if(pics==null) return;
//
//            for (int i = 0; i < pics.size(); i++) {
//                ImageView imageView = (ImageView) getChildAt(i);
//                loader.displayImage(pics.get(0), imageView);
//                imageView.layout(l, t, r, b);
//            }
    }

    public Bitmap processPicture(Bitmap bitmap){
        return null;
    }

    public void setPics(ArrayList<String> pic_urls, Context context) {
        for(int i=0;i<pic_urls.size();i++){

        }
    }
}
