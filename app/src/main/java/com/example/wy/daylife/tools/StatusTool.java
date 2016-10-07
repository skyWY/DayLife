package com.example.wy.daylife.tools;

import android.content.Context;
import android.text.TextUtils;

import com.example.wy.daylife.Interface.Constants;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.legacy.StatusesAPI;
import com.sina.weibo.sdk.openapi.models.ErrorInfo;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.StatusList;
import com.sina.weibo.sdk.openapi.models.User;
import com.sina.weibo.sdk.utils.LogUtil;

import java.util.ArrayList;

/**
 * Created by wy on 2016/10/6.
 */

public class StatusTool {

    /** 当前 Token 信息 */
    private Oauth2AccessToken mAccessToken;

    private StatusesAPI statusesAPI;

    private Context context;
    private static Status status;

    private static String TAG="StatusTool";

    public StatusTool(){

    }

    public StatusTool(Context context){
        this.context=context;
        // 获取当前已保存过的 Token
        mAccessToken = AccessTokenKeeper.readAccessToken(context);
        // 获取用户信息接口
       statusesAPI=new StatusesAPI(context,Constants.APP_KEY,mAccessToken);
    }

    public void getfriendsTimeline(final StatusCallBack statusCallBack){

        /**
         * 微博 OpenAPI 回调接口。
         */
        RequestListener mListener = new RequestListener() {
            @Override
            public void onComplete(String response) {
                if (!TextUtils.isEmpty(response)) {
                    LogUtil.i(TAG, response);
                    statusCallBack.getStatus(StatusList.parse(response).statusList);
                }
            }

            @Override
            public void onWeiboException(WeiboException e) {
                LogUtil.e(TAG, e.getMessage());
                ErrorInfo info = ErrorInfo.parse(e.getMessage());
            }
        };

        statusesAPI.friendsTimeline(0,0,50,1,false,0,false,mListener);
    }

    public interface StatusCallBack{
        void getStatus(ArrayList<Status> statuses);
    }

}
