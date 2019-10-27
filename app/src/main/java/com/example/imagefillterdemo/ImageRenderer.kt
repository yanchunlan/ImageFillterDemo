package com.example.imagefillterdemo

import android.content.Context
import android.graphics.Bitmap
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.GLUtils
import android.opengl.Matrix
import com.example.imagefillterdemo.utils.BufferUtils
import com.example.imagefillterdemo.utils.ShaderUtils
import com.example.imagefillterdemo.utils.TextureUtils
import java.nio.FloatBuffer

import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * author:  ycl
 * date:  2019/10/26 23:16
 * desc:
 */
class ImageRenderer : GLSurfaceView.Renderer {

    private val context: Context
    private val bitmap: Bitmap

    private val vertexData = floatArrayOf(
        -1.0f, 1.0f,
        -1.0f, -1.0f,
        1.0f, 1.0f,
        1.0f, -1.0f
    )
    private val vertexBuffer: FloatBuffer


    // 0-1之间纹理间距
    private val fragmentData = floatArrayOf(
        0.0f, 0.0f,
        0.0f, 1.0f,
        1.0f, 0.0f,
        1.0f, 1.0f
    )
    private val fragmentBuffer: FloatBuffer


    private val viewMatrix = FloatArray(16)
    private val projectMatrix = FloatArray(16)
    private val mvpMatrix = FloatArray(16)

    private var program: Int = 0
    private var glMatrix: Int = 0
    private var glPosition: Int = 0
    private var glCoordinate: Int = 0
    private var glTexture: Int = 0
    private var glChangeType: Int = 0
    private var glChangeColor: Int = 0

    var filter: ImageFilter = ImageFilter.NONE


    constructor(context: Context, bitmap: Bitmap) {
        this.context = context
        this.bitmap = bitmap
        this.vertexBuffer = BufferUtils.arr2FloatBuffer(vertexData)
        this.fragmentBuffer = BufferUtils.arr2FloatBuffer(fragmentData)
    }

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        GLES20.glClearColor(1f, 1f, 1f, 1f)
        GLES20.glEnable(GLES20.GL_TEXTURE_2D)

        program = ShaderUtils.createProgram(context, R.raw.vertex_shader, R.raw.fragment_shader)

        glPosition = GLES20.glGetAttribLocation(program, "vPosition")
        glCoordinate = GLES20.glGetAttribLocation(program, "vCoordinate")
        glMatrix = GLES20.glGetUniformLocation(program, "vMatrix")
        glTexture = GLES20.glGetUniformLocation(program, "vTexture")
        glChangeType = GLES20.glGetUniformLocation(program, "vChangeType")
        glChangeColor = GLES20.glGetUniformLocation(program, "vChangeColor")
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)

        val w = bitmap.width
        val h = bitmap.height
        val sWH = w / h.toFloat()
        val sWidthHeight = width / height.toFloat()
        if (width > height) {
            if (sWH > sWidthHeight) {
                Matrix.orthoM(projectMatrix, 0, -sWidthHeight * sWH, sWidthHeight * sWH, -1f, 1f, 3f, 7f)
            } else {
                Matrix.orthoM(projectMatrix, 0, -sWidthHeight / sWH, sWidthHeight / sWH, -1f, 1f, 3f, 7f)
            }
        } else {
            if (sWH > sWidthHeight) {
                Matrix.orthoM(projectMatrix, 0, -1f, 1f, -1 / sWidthHeight * sWH, 1 / sWidthHeight * sWH, 3f, 7f)
            } else {
                Matrix.orthoM(projectMatrix, 0, -1f, 1f, -sWH / sWidthHeight, sWH / sWidthHeight, 3f, 7f)
            }
        }
        //设置相机位置
        Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, 7.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f)
        //计算变换矩阵
        Matrix.multiplyMM(mvpMatrix, 0, projectMatrix, 0, viewMatrix, 0)
    }

    override fun onDrawFrame(gl: GL10) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
        GLES20.glUseProgram(program)

        GLES20.glUniform1i(glChangeType, filter.type)
        GLES20.glUniform3fv(glChangeColor, 1, filter.data(), 0)
        GLES20.glUniformMatrix4fv(glMatrix, 1, false, mvpMatrix, 0)

//        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glUniform1i(glTexture, 0)
        TextureUtils.genTexturesWithParameter(1, IntArray(1), 0, bitmap)

        //传入顶点坐标
        GLES20.glEnableVertexAttribArray(glPosition)
        GLES20.glVertexAttribPointer(glPosition, 2, GLES20.GL_FLOAT, false, 0, vertexBuffer)

        //传入纹理坐标
        GLES20.glEnableVertexAttribArray(glCoordinate)
        GLES20.glVertexAttribPointer(glCoordinate, 2, GLES20.GL_FLOAT, false, 0, fragmentBuffer)


        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)

        // 释放
//        GLES20.glDisableVertexAttribArray(glPosition)
//        GLES20.glDisableVertexAttribArray(glCoordinate)
    }
}
