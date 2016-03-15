package com.coolrandy.com.opengldemo;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import com.coolrandy.com.opengldemo.view.Square;
import com.coolrandy.com.opengldemo.view.Triangle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by admin on 2016/3/10.
 * 在绘制任何图形之前，必须首先初始化加载你准备绘制的形状shapes
 * 如果将要绘制的shapes不是在执行期绘制的，都应该在onSurfaceCreated进行初始化
 *
 * 绘制一个图形，需要提供很多graphics rendering pipeline的细节，尤其需要定义：
 * Vertex Shader
 * Fragment Shader
 * Program
 */
public class MyGL20Renderer implements GLSurfaceView.Renderer {

    private Triangle triangle;
    private Square square;

    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

    //旋转矩阵
    private float[] mRotationMatrix = new float[16];
    private volatile float mAngle;

    public float getmAngle() {
        return mAngle;
    }

    public void setmAngle(float mAngle) {
        this.mAngle = mAngle;
    }

    // Called when the surface is created or recreated.  设置一些绘制时不常变化的参数  比如：背景色，是否打开 z-buffer等
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // Set the background color to black ( rgba ).
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);//rgba
        // Enable Smooth Shading, default not really needed.
//        gl.glShadeModel(GL10.GL_SMOOTH);
        // Depth buffer setup.
//        GLES20.glClearDepthf(1.0f);// OpenGL docs.
        // Enables depth testing.
//        GLES20.glEnable(GL10.GL_DEPTH_TEST);// OpenGL docs.
        // The type of depth testing to do.
//        GLES20.glDepthFunc(GL10.GL_LEQUAL);// OpenGL docs.
        // Really nice perspective calculations.
//        GLES20.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, // OpenGL docs.
//                GL10.GL_NICEST);
        triangle = new Triangle();
//        square = new Square();
    }

    // Called to draw the current frame.  定义实际的绘图操作
    @Override
    public void onDrawFrame(GL10 gl) {
        float[] scratch = new float[16];
        //重绘
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        //Set the camera position
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        // Create a rotation transformation for the triangle
//        long time = SystemClock.uptimeMillis() % 4000L;
//        float angle = 0.090f * ((int) time);
        Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, 1.0f);
        //Calculate the projection and view transformation
//        android.opengl.Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        // Combine the rotation matrix with the projection and camera view
        // Note that the mMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);
        //draw shape
        triangle.draw(scratch);
    }

    // Called when the surface changed size.  如果设备支持屏幕横向和纵向切换，这个方法将发生在横向<->纵向互换时。此时可以重新设置绘制的纵横比率
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Sets the current view port to the new size.
        GLES20.glViewport(0, 0, width, height);
        // Select the projection matrix
//        gl.glMatrixMode(GL10.GL_PROJECTION);
        // Reset the projection matrix
//        gl.glLoadIdentity();
        // Calculate the aspect ratio of the window
//        GLU.gluPerspective(gl, 45.0f,
//                (float) width / (float) height,
//                0.1f, 100.0f);
        // Select the modelview matrix
//        gl.glMatrixMode(GL10.GL_MODELVIEW);
        // Reset the modelview matrix
//        gl.glLoadIdentity();
        float ratio = (float) width / height;
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);

    }


    public static int loadShader(int type, String shaderCode){

        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }

    /**
     * Utility method for debugging OpenGL calls. Provide the name of the call
     * just after making it:
     *
     * <pre>
     * mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
     * MyGLRenderer.checkGlError("glGetUniformLocation");</pre>
     *
     * If the operation is not successful, the check throws an error.
     *
     * @param glOperation - Name of the OpenGL call to check.
     */
    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e("TAG", glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }
}
