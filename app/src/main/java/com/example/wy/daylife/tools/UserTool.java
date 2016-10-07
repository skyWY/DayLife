package com.example.wy.daylife.tools;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.wy.daylife.Interface.Constants;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.models.ErrorInfo;
import com.sina.weibo.sdk.openapi.models.User;
import com.sina.weibo.sdk.utils.LogUtil;

/**
 * Created by wy on 2016/10/5.
 */

public class UserTool {
    /** 当前 Token 信息 */
    private Oauth2AccessToken mAccessToken;
    /** 用户信息接口 */
    private UsersAPI mUsersAPI;

    private Context context;
    private static User user;

    private static String TAG="UserTool";

    public UserTool(){

    }

    public UserTool(Context context){
        this.context=context;
        // 获取当前已保存过的 Token
        mAccessToken = AccessTokenKeeper.readAccessToken(context);
        // 获取用户信息接口
        mUsersAPI = new UsersAPI(context, Constants.APP_KEY, mAccessToken);


    }

    public void getUser(final UserCallBack userCallBack){

        /**
         * 微博 OpenAPI 回调接口。
         */
         RequestListener mListener = new RequestListener() {
            @Override
            public void onComplete(String response) {
                if (!TextUtils.isEmpty(response)) {
                    LogUtil.i(TAG, response);
                    // 调用 User#parse 将JSON串解析成User对象
                    user = User.parse(response);
                    userCallBack.getUser(user);
                }
            }

            @Override
            public void onWeiboException(WeiboException e) {
                LogUtil.e(TAG, e.getMessage());
                ErrorInfo info = ErrorInfo.parse(e.getMessage());
            }
        };

        //String uid = mAccessToken.getUid();
        long uid = Long.parseLong(mAccessToken.getUid());
        mUsersAPI.show(uid, mListener);
    }

    public interface UserCallBack{
         void getUser(User user);
    }
}
