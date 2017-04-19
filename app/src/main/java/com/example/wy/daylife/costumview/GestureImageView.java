package com.example.wy.daylife.costumview;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * Created by wy on 2017/4/19.
 */

public class GestureImageView extends ImageView implements ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener, ViewTreeObserver.OnGlobalLayoutListener{

    //控件宽度
    private int mWidth;
    //控件高度
    private int mHeight;
    //imageview src的图片
    private Drawable mDrawable;
    //图片的宽度
    private int mDrawWidth;
    //图片的高度
    private int mDrawHeight;
    //初始化缩放值
    private float mScale;
    //双击缩放值
    private float mDoubleClickScale;
    //最大缩放值
    private float mMaxScale;
    //最小缩放值
    private float mMinScale;
    //缩放手势检测
    private ScaleGestureDetector mGestureDetector;
    //当前有着缩放值、平移值的矩阵，图片的缩放、平移可以通过设置矩阵中的值实现
    private Matrix mMatrix;
    private float mMScale;


    public GestureImageView(Context context) {
        this(context,null);
    }

    public GestureImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GestureImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //因为用矩阵实现缩放，平移，所以强制设置imageview的模式为矩阵
        super.setScaleType(ScaleType.MATRIX);
        setOnTouchListener(this);
        mGestureDetector=new ScaleGestureDetector(context,this);
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    //只有返回true手势缩放才会生效
    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        if (mDrawable == null) {
            return true;
        }
        // 系统定义的缩放值
        //scaleFactor是获得手势缩放的值，当值>1.0f时，说明两个手指的滑动距离是不断增加（相对于两个手指都down了的那一瞬间），<1.0f说明两个手指的滑动距离不断减少，也是相对于那一瞬间，
        float scaleFactor = detector.getScaleFactor();
        // 获取已经缩放的值
        float scale = getmScale();
        float scaleResult = scale * scaleFactor;
        if (scaleResult >= mMaxScale && scaleFactor > 1.0f)
            scaleFactor = mMaxScale / scale;
        if (scaleResult <= mMinScale && scaleFactor < 1.0f)
            scaleFactor = mMinScale / scale;


        mMatrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());

        RectF f = getRectf(mMatrix);
        float dX = 0.0f;
        float dY = 0.0f;
        // 图片高度大于控件高度
        if (f.height() >= mHeight) {
            // 图片顶部出现空白
            if (f.top > 0) {
                // 往上移动
                dY = -f.top;
            }
            // 图片底部出现空白
            if (f.bottom < mHeight) {
                // 往下移动
                dY = mHeight - f.bottom;
            }
        }
        // 图片宽度大于控件宽度
        if (f.width() >= mWidth) {
            // 图片左边出现空白
            if (f.left > 0) {
                // 往左边移动
                dX = -f.left;
            }
            // 图片右边出现空白
            if (f.right < mWidth) {
                // 往右边移动
                dX = mWidth - f.right;
            }
        }

        if (f.width() < mWidth) {
            dX = mWidth / 2 - f.right + f.width() / 2;
        }

        if (f.height() < mHeight) {
            dY = mHeight / 2 - f.bottom + f.height() / 2;
        }
        mMatrix.postTranslate(dX, dY);

        setImageMatrix(mMatrix);
        return true;
    }

    /**
     * @param matrix 矩阵
     * @return matrix的 l t b r 和width，height
     */
    private RectF getRectf(Matrix matrix) {
        RectF f = new RectF();
        if (mDrawable == null)
            return null;
        f.set(0, 0, mDrawWidth, mDrawHeight);
        matrix.mapRect(f);
        return f;
    }

    /**
     * @return 当前缩放的值
     */
    private float getmScale() {
        float[] floats = new float[9];
        mMatrix.getValues(floats);
        return floats[Matrix.MSCALE_X];
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        float scale = getmScale();
        if (scale < mScale) {
            mMatrix.postScale(mScale / scale, mScale / scale, mWidth / 2, mHeight / 2);
            setImageMatrix(mMatrix);
        }
    }

    private float downX;
    private float downY;
    private float nowMovingX;
    private float nowMovingY;
    private float lastMovedX;
    private float lastMovedY;
    private boolean isFirstMoved = false;

    //虽然实现了监听，但是然并卵，因为onTouch事件中没有它（scaleGestureDetector）
    // 在这里 ，重写onTouchEvent是没用的，因为onTouchEventListener的优先级比onTouchEvent要高
    // 所以只能在touch事件中调用mGestureDetector的touch方法
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                isFirstMoved = false;
                downX = event.getX();
                downY = event.getY();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                isFirstMoved = false;
                break;
            case MotionEvent.ACTION_MOVE:
                nowMovingX = event.getX();
                nowMovingY = event.getY();
                if (!isFirstMoved) {
                    isFirstMoved = true;
                    lastMovedX = nowMovingX;
                    lastMovedY = nowMovingY;
                }
                float dX = 0.0f;
                float dY = 0.0f;
                RectF rectf = getRectf(mMatrix);
                // 判断滑动方向
                final float scrollX = nowMovingX - lastMovedX;
                // 判断滑动方向
                final float scrollY = nowMovingY - lastMovedY;
                // 图片高度大于控件高度
                if (rectf.height() > mHeight && canSmoothY()) {
                    dY = nowMovingY - lastMovedY;
                }

                // 图片宽度大于控件宽度
                if (rectf.width() > mWidth && canSmoothX()) {
                    dX = nowMovingX - lastMovedX;
                }
                mMatrix.postTranslate(dX, dY);

                remedyXAndY(dX,dY);

                lastMovedX = nowMovingX;
                lastMovedY = nowMovingY;
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                isFirstMoved = false;
                break;
        }
        return mGestureDetector.onTouchEvent(event);
    }

    /**
     * 判断x方向上能不能滑动
     * @return 可以滑动返回true
     */
    private boolean canSmoothX(){
        RectF rectf = getRectf(mMatrix);
        if (rectf.left >0 || rectf.right <getWidth())
            return false;
        return true;
    }

    /**
     * 判断y方向上可不可以滑动
     * @return 可以滑动返回true
     */
    private boolean canSmoothY(){
        RectF rectf = getRectf(mMatrix);
        if (rectf.top>0 || rectf.bottom < getHeight())
            return false;
        return true;
    }

    /**
     * 纠正出界的横和众线
     * @param dx 出界偏移的横线
     * @param dy 出街便宜的众线
     */
    private void remedyXAndY(float dx,float dy){
        if (!canSmoothX())
            mMatrix.postTranslate(-dx,0);
        if (!canSmoothY())
            mMatrix.postTranslate(0,-dy);
        setImageMatrix(mMatrix);
    }

    @Override
    public void onGlobalLayout() {
        //移除观察者,// 当layout执行结束后回调
        //使用完必须撤销监听（只测量一次），否则，会一直不停的不定时的测量，这比较耗性能
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
        //获取控件大小
        mWidth=getWidth();
        mHeight=getHeight();
        //获取src
        mDrawable=getDrawable();
        if(mDrawable==null) return;
        //或取图片大小
        mDrawWidth=mDrawable.getIntrinsicWidth();
        mDrawHeight=mDrawable.getIntrinsicHeight();

        initImageViewSize();
        moveToCenter();
    }

    //初始化资源图片宽高
    private void initImageViewSize() {
        if(mDrawable==null) return;

        //缩放值
        float scale=1.0f;
        // 图片宽度大于控件宽度，图片高度小于控件高度
        if (mDrawWidth > mWidth && mDrawHeight < mHeight)
            scale = mWidth * 1.0f / mDrawWidth;
            // 图片高度度大于控件宽高，图片宽度小于控件宽度
        else if (mDrawHeight > mHeight && mDrawWidth < mWidth)
            scale = mHeight * 1.0f / mDrawHeight;
            // 图片宽度大于控件宽度，图片高度大于控件高度
        else if (mDrawHeight > mHeight && mDrawWidth > mWidth)
            scale = Math.min(mHeight * 1.0f / mDrawHeight, mWidth * 1.0f / mDrawWidth);
            // 图片宽度小于控件宽度，图片高度小于控件高度
        else if (mDrawHeight < mHeight && mDrawWidth < mWidth)
            scale = Math.min(mHeight * 1.0f / mDrawHeight, mWidth * 1.0f / mDrawWidth);

        //设置初始化缩放值和最大最小缩放值
        mScale = scale;
        mMaxScale = mScale * 8.0f;
        mMinScale = mScale * 0.5f;
    }

    //把图片移动到屏幕中心
    private void moveToCenter() {
        final float dx = mWidth / 2 - mDrawWidth / 2;
        final float dy = mHeight / 2 - mDrawHeight / 2;
        mMatrix = new Matrix();
        // 平移至中心
        mMatrix.postTranslate(dx, dy);
        // 以控件中心作为缩放
        mMatrix.postScale(mScale, mScale, mWidth / 2, mHeight / 2);
        setImageMatrix(mMatrix);
    }

}
