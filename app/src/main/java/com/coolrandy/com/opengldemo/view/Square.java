package com.coolrandy.com.opengldemo.view;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by admin on 2016/3/15.
 * 有多重方式绘制矩形，一种典型的方式是使用两个三角形一起来绘制
 */
public class Square {

    static final int COORDS_PER_VERTEX = 3;
    //定义一个顶点数组  分别代表四个顶点坐标
    private float vertices[] = {
            -1.0f, 1.0f, 0.0f,//0 Top Left
            -1.0f, 1.0f, 0.0f,//1 Bottom Left
            -1.0f, 1.0f, 0.0f,//2 Bottom right
            -1.0f, 1.0f, 0.0f,//3 Top Right
    };
    //order to draw vertices  胡子豪顶点的顺序
    private short drawOrder[] = {0, 1, 2, 0, 2, 3};
    private FloatBuffer vertexBuffer;
    private ShortBuffer drawListBuffer;

    public Square() {

        //一个浮点型数据为4byte，所以将定点数乘以4
        //为提升性能，采用java.nio的ByteBuffer来存储节点
        ByteBuffer vbb = ByteBuffer.allocate(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vertexBuffer = vbb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        ByteBuffer dlb = ByteBuffer.allocate(vertices.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);
    }
}
