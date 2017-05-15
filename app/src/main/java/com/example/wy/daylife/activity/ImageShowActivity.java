package com.example.wy.daylife.activity;

import android.graphics.PointF;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

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

            GestureImageView imageView=new GestureImageView(ImageShowActivity.this);
            loader.displayImage(list.get(position),imageView);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            container.addView(imageView);
            return imageView;

//            final SubsamplingScaleImageView imageView = new SubsamplingScaleImageView(ImageShowActivity.this);
//
//            imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
//            imageView.setMinScale(1.0F);
//            Log.i(TAG,list.get(position));
//
//            //下载图片保存到本地
//            Glide.with(ImageShowActivity.this)
//                    .load(list.get(position)).downloadOnly(new SimpleTarget<File>() {
//                @Override
//                public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
//                    // 将保存的图片地址给SubsamplingScaleImageView,这里注意设置ImageViewState设置初始显示比例
//                    imageView.setImage(ImageSource.uri(Uri.fromFile(resource)), new ImageViewState(2.0F, new PointF(0, 0), 0));
//                }});
//            return imageView;
        }
    };
}
