package com.example.wy.daylife.costumview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by wy on 2017/3/14.
 */

public class CustomLinearLayout extends LinearLayout {
    public CustomLinearLayout(Context context) {
        this(context,null);
    }

    public CustomLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
