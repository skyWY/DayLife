package com.example.wy.daylife.costumview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.widget.LinearLayout;
import android.widget.Scroller;

/**
 * Created by wy on 2017/3/14.
 */

public class CustomLinearLayout extends LinearLayout {

    //分别记录上次滑动的坐标
    private int mLastX=0;
    private int mLastY=0;

    //分别记录上次滑动的坐标(onInterceptTouchEvent)
    private int mLastXIntercept=0;
    private int mLastYIntercept=0;

    private VelocityTracker mVelocityTracker;
    private Scroller mScroller;

    public CustomLinearLayout(Context context) {
        this(context,null);
    }

    public CustomLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if(mScroller==null){
            mScroller=new Scroller(getContext());
            mVelocityTracker=VelocityTracker.obtain();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted=false;
        int x= (int) ev.getX();
        int y= (int) ev.getY();

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                intercepted=false;
                if(!mScroller.isFinished()){
                    mScroller.abortAnimation();
                    intercepted=true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX=x-mLastXIntercept;
                int deltaY=y-mLastYIntercept;

                if(Math.abs(deltaY)>Math.abs(deltaX)){

                    if(getChildAt(0).getY()==0 && deltaY<0){
                        intercepted=true;
                    }
                }else{
                    intercepted=false;
                }
                break;
            case MotionEvent.ACTION_UP:
                intercepted=false;
                break;

        }

        mLastX=x;
        mLastY=y;
        mLastXIntercept=x;
        mLastYIntercept=y;

        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        mVelocityTracker.addMovement(event);

        int x= (int) event.getX();
        int y= (int) event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX=x-mLastX;
                int deltaY=y-mLastY;

                scrollTo(0,-deltaY);

                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        mLastX=x;
        mLastY=y;
        return true;
    }

    //滑动到指定位置
    private void smoothScrollTo(int destX,int destY){
        int scrollY=getScrollY();
        int delta=destY-scrollY;
        mScroller.startScroll(0,scrollY,0,delta,1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }
}
