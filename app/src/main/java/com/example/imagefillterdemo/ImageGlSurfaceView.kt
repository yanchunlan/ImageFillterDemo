package com.example.imagefillterdemo

import android.content.Context
import android.graphics.BitmapFactory
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import kotlin.properties.Delegates

/**
 * author:  ycl
 * date:  2019/10/26 23:17
 * desc:
 */
class ImageGlSurfaceView : GLSurfaceView {

    var renderer: ImageRenderer by Delegates.notNull<ImageRenderer>()

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    init {
        init()
    }

    private fun init() {
        setEGLContextClientVersion(2)
        renderer = ImageRenderer(context,BitmapFactory.decodeResource(resources, R.mipmap.ic_cat))
        setRenderer(renderer)
        renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    }
}
