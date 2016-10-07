package com.example.wy.daylife.tools;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.wy.daylife.Interface.Constants;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.UsersAPI;
import com.sina.weibo.sdk.openapi.legacy.GroupAPI;
import com.sina.weibo.sdk.openapi.models.ErrorInfo;
import com.sina.weibo.sdk.openapi.models.Group;
import com.sina.weibo.sdk.openapi.models.GroupList;
import com.sina.weibo.sdk.openapi.models.User;
import com.sina.weibo.sdk.utils.LogUtil;

/**
 * Created by wy on 2016/10/5.
 */

public class GroupTool {

    private static String TAG="GroupTool";

    /** 当前 Token 信息 */
    private Oauth2AccessToken mAccessToken;
    /** 用户信息接口 */
    private GroupAPI mGroupAPI;

    private Context context;

    public GroupTool(){

    }

    public GroupTool(Context context){
        this.context=context;
        // 获取当前已保存过的 Token
        mAccessToken = AccessTokenKeeper.readAccessToken(context);
        // 获取用户信息接口
        mGroupAPI = new GroupAPI(context, Constants.APP_KEY, mAccessToken);
    }



    public void getGroup(final GroupCallBack callBack){

         RequestListener mListener=new RequestListener(){

             @Override
             public void onComplete(String s) {
                 if (!TextUtils.isEmpty(s)) {
                     LogUtil.i(TAG, s);
                     // 调用 User#parse 将JSON串解析成User对象
                     GroupList groupList=GroupList.parse(s);
                     callBack.getGroup(groupList);
                 }
             }

             @Override
             public void onWeiboException(WeiboException e) {
                 LogUtil.e(TAG, e.getMessage());
                 ErrorInfo info = ErrorInfo.parse(e.getMessage());
                 Toast.makeText(context,info.error_code+"",Toast.LENGTH_LONG).show();
             }
         };

        mGroupAPI.groups( mListener);
    }

    public interface GroupCallBack{
        void getGroup(GroupList groupList);
    }
}
