package com.example.wy.daylife.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.wy.daylife.R;
import com.example.wy.daylife.tools.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

public class SplashActivity extends AppCompatActivity {

    //延迟3秒
    private static final long SPLASH_DELAY_MILLIS = 1000;
    /** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能  */
    private Oauth2AccessToken mAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       /*set it to be full screen*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);
        goHome();
    }

    private void goHome() {
        // 使用Handler的postDelayed方法，3秒后执行跳转到MainActivity
        mAccessToken= AccessTokenKeeper.readAccessToken(this);
        if(mAccessToken.isSessionValid()){
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, SPLASH_DELAY_MILLIS);
        }else{
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, WBAuthActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, SPLASH_DELAY_MILLIS);
        }
    }
}
