package com.example.wy.daylife.activity;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.wy.daylife.R;
import com.example.wy.daylife.base.BaseActivity;
import com.example.wy.daylife.costumview.GestureImageView;
import com.example.wy.daylife.tools.ImageLoaderTool;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;

public class ImageShowActivity extends BaseActivity {

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
        }
    };
}
