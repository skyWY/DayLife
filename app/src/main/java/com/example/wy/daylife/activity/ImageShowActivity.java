package com.example.wy.daylife.activity;

import android.graphics.PointF;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.wy.daylife.R;
import com.example.wy.daylife.base.BaseActivity;
import com.example.wy.daylife.costumview.GestureImageView;
import com.example.wy.daylife.tools.ImageLoaderTool;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.util.List;

import butterknife.Bind;


public class ImageShowActivity extends BaseActivity {

    private static final String TAG = "ImageShowActivity";
    private List<String> list;
    @Bind(R.id.img_show_viewpager)
    public ViewPager viewPager;

    private ImageLoader loader;
    private int position;

    @Override
    public int getLayoutId() {
        return R.layout.activity_image_show;
    }

    @Override
    public void initData() {
        list=getIntent().getStringArrayListExtra("imgUrl");
        position=getIntent().getIntExtra("position",0);
        loader= ImageLoaderTool.getInstance(this);
    }

    @Override
    public void initEvent() {

    }

    @Override
    protected void onDestroy() {
        finish();
        super.onDestroy();
    }

    @Override
    public void initView() {
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
    }

    @Override
    public void initTheme() {

    }

    PagerAdapter adapter=new PagerAdapter() {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
 //           container.removeView(viewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            if(list.get(position).endsWith(".gif")){
                ImageView imageView=new ImageView(ImageShowActivity.this);
                Glide.with(ImageShowActivity.this).load(list.get(position)).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageView);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                //imageView.play();
                container.addView(imageView);
                return imageView;
            }else {
                GestureImageView imageView=new GestureImageView(ImageShowActivity.this);
                loader.displayImage(list.get(position),imageView);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                container.addView(imageView);
                return imageView;
            }

//            GestureImageView imageView=new GestureImageView(ImageShowActivity.this);
//            loader.displayImage(list.get(position),imageView);
//            imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//            container.addView(imageView);
//            return imageView;

        }
    };
}
