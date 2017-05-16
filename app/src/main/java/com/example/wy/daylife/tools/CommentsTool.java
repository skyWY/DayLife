package com.example.wy.daylife.tools;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.example.wy.daylife.Interface.Constants;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.CommentsAPI;
import com.sina.weibo.sdk.openapi.models.Comment;
import com.sina.weibo.sdk.openapi.models.CommentList;
import com.sina.weibo.sdk.openapi.models.ErrorInfo;


import java.util.ArrayList;

/**
 * Created by wy on 2016/10/6.
 */

public class CommentsTool {

    /** 当前 Token 信息 */
    private Oauth2AccessToken mAccessToken;

    private CommentsAPI commentsAPI;

    private Context context;

    private static String TAG="CommentsTool";

    private long since_id;
    private long max_id;
    private long id;

    public CommentsTool(){

    }


    public CommentsTool(Context context, long since_id, long max_id,long id){
        this.context=context;
        this.since_id=since_id;
        this.max_id=max_id;
        this.id=id;
        // 获取当前已保存过的 Token
        mAccessToken = AccessTokenKeeper.readAccessToken(context);
        // 获取用户信息接口
       commentsAPI=new CommentsAPI(context,Constants.APP_KEY,mAccessToken);
    }

    public void getWBComments(final StatusCallBack statusCallBack){

        /**
         * 微博 OpenAPI 回调接口。
         */
        RequestListener mListener = new RequestListener() {
            @Override
            public void onComplete(String response) {
                if (!TextUtils.isEmpty(response)) {
                    Log.i(TAG, response);
                    statusCallBack.getComments(CommentList.parse(response).commentList);
                }
            }

            @Override
            public void onWeiboException(WeiboException e) {
                Log.i(TAG, e.getMessage());
                ErrorInfo info = ErrorInfo.parse(e.getMessage());
            }
        };

        commentsAPI.show(id,since_id,max_id,50,1,0,mListener);
    }

    public interface StatusCallBack{
        void getComments(ArrayList<Comment> statuses);
    }
}
