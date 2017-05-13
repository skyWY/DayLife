package com.example.wy.daylife.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wy.daylife.R;
import com.example.wy.daylife.adapter.EmotionGvAdapter;
import com.example.wy.daylife.adapter.EmotionPagerAdapter;
import com.example.wy.daylife.base.BaseActivity;
import com.example.wy.daylife.costumview.GridViewImage;
import com.example.wy.daylife.tools.DisplayUtils;
import com.example.wy.daylife.tools.EmotionUtils;
import com.example.wy.daylife.tools.ScreenUtil;
import com.example.wy.daylife.tools.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.Bind;


public class PostMessegeActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    @Bind(R.id.post_positon)
    public ImageButton positon;
    @Bind(R.id.post_picture)
    public ImageButton picture;
    @Bind(R.id.post_emotion)
    public ImageButton emotion;
    @Bind(R.id.post_at)
    public ImageButton person;
    @Bind(R.id.post_post)
    public ImageButton send;
    @Bind(R.id.editext)
    public EditText et_emotion;
    @Bind(R.id.ll_emotion_dashboard)
    public LinearLayout ll_emotion_dashboard;
    @Bind(R.id.vp_emotion_dashboard)
    public ViewPager vp_emotion_dashboard;
    @Bind(R.id.toolbar)
    public Toolbar toolbar;
    @Bind(R.id.tool_group)
    public TextView textView;
    @Bind(R.id.post_picture_container)
    public GridViewImage layout;

    private EmotionPagerAdapter emotionPagerGvAdapter;

    private static String path="/sdcard/myHead/";//sd路径
    private static final int REQUESTCODE_PHOTO=1;
    private static final int REQUESTCODE_FANHUI=2;

    private static String TAG="PostMessegeActivity";

    private int picWidth=0;
    private LayoutInflater inflater=null;

    @Override
    public int getLayoutId() {
        return R.layout.activity_post_messege;
    }

    /**
     *  初始化表情面板内容
     */
    private void initEmotion() {
        // 获取屏幕宽度
        int gvWidth = DisplayUtils.getScreenWidthPixels(this);
        // 表情边距
        int spacing = DisplayUtils.dp2px(this, 8);
        // GridView中item的宽度
        int itemWidth = (gvWidth - spacing * 8) / 7;
        int gvHeight = itemWidth * 3 + spacing * 4;

        List<GridView> gvs = new ArrayList<GridView>();
        List<String> emotionNames = new ArrayList<String>();
        // 遍历所有的表情名字
        for (String emojiName : EmotionUtils.emojiMap.keySet()) {
            emotionNames.add(emojiName);
            // 每20个表情作为一组,同时添加到ViewPager对应的view集合中
            if (emotionNames.size() == 20) {
                GridView gv = createEmotionGridView(emotionNames, gvWidth, spacing, itemWidth, gvHeight);
                gvs.add(gv);
                // 添加完一组表情,重新创建一个表情名字集合
                emotionNames = new ArrayList<String>();
            }
        }

        // 检查最后是否有不足20个表情的剩余情况
        if (emotionNames.size() > 0) {
            GridView gv = createEmotionGridView(emotionNames, gvWidth, spacing, itemWidth, gvHeight);
            gvs.add(gv);
        }

        // 将多个GridView添加显示到ViewPager中
        emotionPagerGvAdapter = new EmotionPagerAdapter(gvs);
        vp_emotion_dashboard.setAdapter(emotionPagerGvAdapter);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(gvWidth, gvHeight);
        vp_emotion_dashboard.setLayoutParams(params);
    }

    /**
     * 创建显示表情的GridView
     */
    private GridView createEmotionGridView(List<String> emotionNames, int gvWidth, int padding, int itemWidth, int gvHeight) {
        // 创建GridView
        GridView gv = new GridView(this);
        gv.setBackgroundColor(Color.rgb(233, 233, 233));
        gv.setSelector(android.R.color.transparent);
        gv.setNumColumns(7);
        gv.setPadding(padding, padding, padding, padding);
        gv.setHorizontalSpacing(padding);
        gv.setVerticalSpacing(padding);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(gvWidth, gvHeight);
        gv.setLayoutParams(params);
        // 给GridView设置表情图片
        EmotionGvAdapter adapter = new EmotionGvAdapter(this, emotionNames, itemWidth);
        gv.setAdapter(adapter);
        gv.setOnItemClickListener(this);
        return gv;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object itemAdapter = parent.getAdapter();

        if (itemAdapter instanceof EmotionGvAdapter) {
            // 点击的是表情
            EmotionGvAdapter emotionGvAdapter = (EmotionGvAdapter) itemAdapter;

            if (position == emotionGvAdapter.getCount() - 1) {
                // 如果点击了最后一个回退按钮,则调用删除键事件
                et_emotion.dispatchKeyEvent(new KeyEvent(
                        KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
            } else {
                // 如果点击了表情,则添加到输入框中
                String emotionName = emotionGvAdapter.getItem(position);

                // 获取当前光标位置,在指定位置上添加表情图片文本
                int curPosition = et_emotion.getSelectionStart();
                StringBuilder sb = new StringBuilder(et_emotion.getText().toString());
                sb.insert(curPosition, emotionName);

                // 特殊文字处理,将表情等转换一下
                et_emotion.setText(StringUtils.getEmotionContent(
                        this, (int)et_emotion.getTextSize(), sb.toString()));

                // 将光标设置到新增完表情的右侧
                et_emotion.setSelection(curPosition + emotionName.length());
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){

            case REQUESTCODE_PHOTO:
                if(resultCode==RESULT_OK){
                    cropPhoto(data.getData());
                }
                break;
            case REQUESTCODE_FANHUI:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    Bitmap head = extras.getParcelable("data");
//                    UUID uuid=UUID.randomUUID();
//                    String fileName =uuid+".jpg";//图片名字
//                    setPicToView(head,fileName);
//                    int curPosition = et_emotion.getSelectionStart();
//                    StringBuilder sb = new StringBuilder(et_emotion.getText().toString());
//                    sb.insert(curPosition, "["+fileName+"]");
//
//                    et_emotion.setText(StringUtils.getPictureContent(
//                            this, (int)et_emotion.getTextSize(), sb.toString()));
//
//                    // 将光标设置到图片的右侧
//                    et_emotion.setSelection(curPosition + fileName.length());
                    View view=inflater.inflate(R.layout.post_picture,null);
                    ImageView imageView= (ImageView) view.findViewById(R.id.picture);
                    imageView.setImageBitmap(head);
                    layout.addView(view,picWidth,picWidth);
                }
                break;
        }
    }

    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUESTCODE_FANHUI);
    }

    private void setPicToView(Bitmap mBitmap,String fileName) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File dirFile = new File(path);
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }
        fileName =path +fileName;//图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
            Log.i(TAG,"save success");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if(b!=null) {
                    //关闭流
                    b.flush();
                    b.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void initData() {
        picWidth=(ScreenUtil.getScreenW(this)-10)/3;
        inflater=getLayoutInflater();
    }

    @Override
    public void initEvent() {
        emotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 隐藏或显示表情面板
                ll_emotion_dashboard.setVisibility(
                        ll_emotion_dashboard.getVisibility() == View.VISIBLE ?
                                View.GONE : View.VISIBLE);
            }
        });

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(pickIntent, REQUESTCODE_PHOTO);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void initView() {
        setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textView.setText("发微博");
        initEmotion();
    }

    @Override
    public void initTheme() {
    }
}
