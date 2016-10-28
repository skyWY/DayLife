package com.example.wy.daylife.tools;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.widget.TextView;

import com.example.wy.daylife.Interface.Defs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	private static String TAG="StringUtils";

	public static SpannableString getEmotionContent(final Context context, int size, String source) {
		SpannableString spannableString = new SpannableString(source);
		Resources res = context.getResources();

		//这个正则表达式表示至少匹配一个汉字，\u4e00,\u9fa5,分别是unicode编码中汉字的头和尾的编码，+表示至少一个
		//android中[有特殊意思，所以要转义\\[，（）表示里面的为一个group
		String regexEmotion = "\\[([\u4e00-\u9fa5\\w])+\\]";
		Pattern patternEmotion = Pattern.compile(regexEmotion);
		Matcher matcherEmotion = patternEmotion.matcher(spannableString);


		while (matcherEmotion.find()) {
			// 获取匹配到的具体字符
			String key = matcherEmotion.group();
			Log.i(TAG,key);
			// 匹配字符串的开始位置
			int start = matcherEmotion.start();
			// 利用表情名字获取到对应的图片
			Integer imgRes = EmotionUtils.getImgByName(key);
			if (imgRes != null && imgRes!=-1) {

				Bitmap bitmap = BitmapFactory.decodeResource(res, imgRes);
				Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);

				ImageSpan span = new ImageSpan(context, scaleBitmap);
				spannableString.setSpan(span, start, start + key.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		}

		return spannableString;
	}

	public static void extractMention2Link(TextView v) {
		v.setAutoLinkMask(0);

		Pattern mentionsPattern = Pattern.compile("@(\\w+?)(?=\\W|$)(.)");
		String mentionsScheme = String.format("%s/?%s=", Defs.MENTIONS_SCHEMA, Defs.PARAM_UID);
		Linkify.addLinks(v, mentionsPattern, mentionsScheme, new Linkify.MatchFilter() {

			@Override
			public boolean acceptMatch(CharSequence s, int start, int end) {
				return s.charAt(end-1) != '.';
			}

		}, new Linkify.TransformFilter() {
			@Override
			public String transformUrl(Matcher match, String url) {
				Log.d(TAG, match.group(1));
				return match.group(1);
			}
		});

		Pattern trendsPattern = Pattern.compile("#(\\w+?)#");
		String trendsScheme = String.format("%s/?%s=", Defs.TRENDS_SCHEMA, Defs.PARAM_UID);
		Linkify.addLinks(v, trendsPattern, trendsScheme, null, new Linkify.TransformFilter() {
			@Override
			public String transformUrl(Matcher match, String url) {
				Log.d(TAG, match.group(1));
				return match.group(1);
			}
		});

		Pattern webPattern = Pattern.compile("http://t.cn/(\\w+)");
		String webScheme = String.format("%s/?%s=", Defs.WEB_SCHEMA, Defs.PARAM_UID);
		Linkify.addLinks(v, webPattern, webScheme, null, new Linkify.TransformFilter() {
			@Override
			public String transformUrl(Matcher match, String url) {
				Log.i(TAG, match.group(0));
				return match.group(0);
			}
		});
	}
}
