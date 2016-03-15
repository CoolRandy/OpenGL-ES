package com.coolrandy.com.opengldemo;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by admin on 2016/3/15.
 */
public class MyGLSurfaceView extends GLSurfaceView{

    private final MyGL20Renderer mRenderer;
    //触摸缩放因子  可以用来调整视图随着手指旋转的角度大小
    private final float TOUCH_SCALE_FACTOR = 180.0f / 1280;
    private float mPreviousX;
    private float mPreviousY;

    public MyGLSurfaceView(Context context) {
        super(context);
        // Create an OpenGL ES 2.0 context. 一定要设置
        setEGLContextClientVersion(2);
        mRenderer = new MyGL20Renderer();
        setRenderer(mRenderer);
//            setEGLContextClientVersion(2);//采用2.0api
        //设置渲染模式,仅当绘制数据发生改变时才绘制view  该设置可以防止GLSurfaceView帧重绘，直到调用requestRender()方法
        setRenderMode(RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float xPos = event.getX();
        float yPos = event.getY();
        switch (event.getAction()){

            case MotionEvent.ACTION_MOVE:

                Log.e("TAG", "x= " + xPos + ", mPreviousX= " + mPreviousX);
                Log.e("TAG", "y= " + yPos + ", mPreviousY= " + mPreviousY);
                float dx = xPos - mPreviousX;
                float dy = yPos - mPreviousY;

                Log.e("TAG", "dx= " + dx + ", dy= " + dy);
                Log.e("TAG", "height: " + getHeight() / 2);

                // reverse direction of rotation above the mid-line
                if (yPos > getHeight() / 2) {
                        dx = dx * -1 ;
                    Log.e("TAG", "changed dx: " + dx);
                }

                // reverse direction of rotation to left of the mid-line
                if (xPos < getWidth() / 2) {
                    dy = dy * -1 ;
                    Log.e("TAG", "changed dy: " + dy);
                }

                    mRenderer.setmAngle(
                            mRenderer.getmAngle() +
                                    ((dx + dy) * TOUCH_SCALE_FACTOR));
                requestRender();
//                break;

        }
        mPreviousY = yPos;
        mPreviousX = xPos;
        return true;
    }

    /**
     *                                                                  x= 223.03862, mPreviousX= 220.89566   第二象限
     03-15 06:07:15.151 31948-31948/com.coolrandy.com.opengldemo E/TAG: y= 159.5073, mPreviousY= 160.83528
     03-15 06:07:15.151 31948-31948/com.coolrandy.com.opengldemo E/TAG: dx= 2.1429596, dy= -1.3279877       x变大 y变小  顺时针
     03-15 06:07:15.151 31948-31948/com.coolrandy.com.opengldemo E/TAG: height: 567
     03-15 06:07:15.151 31948-31948/com.coolrandy.com.opengldemo E/TAG: changed dy: 1.3279877



                                                                        x= 88.88426, mPreviousX= 90.63886
     03-15 06:09:52.735 31948-31948/com.coolrandy.com.opengldemo E/TAG: y= 207.44696, mPreviousY= 205.66493
     03-15 06:09:52.735 31948-31948/com.coolrandy.com.opengldemo E/TAG: dx= -1.7546005, dy= 1.7820282       x变小 y变大  逆时针
     03-15 06:09:52.735 31948-31948/com.coolrandy.com.opengldemo E/TAG: height: 567
     03-15 06:09:52.735 31948-31948/com.coolrandy.com.opengldemo E/TAG: changed dy: -1.7820282
     */
}
