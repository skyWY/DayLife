package com.example.wy.daylife.tools;

import android.util.Log;

import com.sina.weibo.sdk.utils.LogUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wy on 2016/10/6.
 */

public class RegxTool {

    public static String getWBSource(String source){
        Pattern pattern=Pattern.compile("([\\u4e00-\\u9fa5]+)");
        Matcher matcher=pattern.matcher(source);

        while(matcher.find()){
            return matcher.group(0);
        }
        return "";
    }

    public static String getDate(String date){
        SimpleDateFormat format=new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        try {
            long date1=format.parse(date).getTime();
            long date2=new Date().getTime();
            long m=date2-date1;
            if(m/1000<=60){
                return "刚刚";
            }else if(m/(1000*60)<60){
                return m/(1000*60)+"分钟前";
            }else if(m/(1000*60*60)<24){
                return m/(1000*60*60)+"小时前";
            }else if(m/(1000*60*60)<48){
                return "昨天";
            }else {
                SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd");
                return format.parse(date)+"";
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
