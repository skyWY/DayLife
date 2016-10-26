package com.example.wy.daylife.tools;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

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

    private long since_id;
    private long max_id;

    public StatusTool(){

    }


    public StatusTool(Context context,long since_id,long max_id){
        this.context=context;
        this.since_id=since_id;
        this.max_id=max_id;
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
                    Log.i(TAG, response);
                    statusCallBack.getStatus(StatusList.parse(response).statusList);
                    StatusWriterTool.writeStatus(response);
                }
            }

            @Override
            public void onWeiboException(WeiboException e) {
                Log.i(TAG, e.getMessage());
                ErrorInfo info = ErrorInfo.parse(e.getMessage());
            }
        };

        statusesAPI.friendsTimeline(since_id,max_id,30,1,false,0,false,mListener);
    }

    public interface StatusCallBack{
        void getStatus(ArrayList<Status> statuses);
    }

}
