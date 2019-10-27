package com.example.imagefillterdemo.utils

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer

/**
 * author: ycl
 * date: 2018-10-29 23:37
 * desc:
 */
object BufferUtils {
    /**
     * 将浮点数组转换成字节缓冲区
     */
    fun arr2ByteBuffer(arr: FloatArray): ByteBuffer {
        val ibb = ByteBuffer.allocateDirect(arr.size * 4)
        ibb.order(ByteOrder.nativeOrder())
        val fbb = ibb.asFloatBuffer()
        fbb.put(arr)
        fbb.position(0)
        return ibb
    }

    /**
     * 将byte数组转换成字节缓冲区
     */
    fun arr2ByteBuffer(arr: ByteArray): ByteBuffer {
        val ibb = ByteBuffer.allocateDirect(arr.size)
        ibb.order(ByteOrder.nativeOrder())
        ibb.put(arr)
        ibb.position(0)
        return ibb
    }

    /**
     * 将list转换成字节缓冲区
     */
    fun list2ByteBuffer(arr: List<Float>): ByteBuffer {
        val ibb = ByteBuffer.allocateDirect(arr.size * 4)
        ibb.order(ByteOrder.nativeOrder())
        val fbb = ibb.asFloatBuffer()
        for (f in arr) {
            fbb.put(f)
        }
        fbb.position(0)
        return ibb
    }

    /**
     * 将list转换成浮点缓冲区
     */
    fun list2FloatBuffer(arr: List<Float>): FloatBuffer {
        val ibb = ByteBuffer.allocateDirect(arr.size * 4)
        ibb.order(ByteOrder.nativeOrder())
        val fbb = ibb.asFloatBuffer()
        for (f in arr) {
            fbb.put(f)
        }
        fbb.position(0)
        return fbb
    }

    /**
     * 将float转换成浮点缓冲区
     */
    fun arr2FloatBuffer(arr: FloatArray): FloatBuffer {
        val ibb = ByteBuffer.allocateDirect(arr.size * 4)
        ibb.order(ByteOrder.nativeOrder())
        val fbb = ibb.asFloatBuffer()
        fbb.put(arr)
        fbb.position(0)
        return fbb
    }

    /**
     * 将short转换成short缓冲区
     */
    fun arr2ShortBuffer(arr: ShortArray): ShortBuffer {
        val ibb = ByteBuffer.allocateDirect(arr.size * 4)
        ibb.order(ByteOrder.nativeOrder())
        val fbb = ibb.asShortBuffer()
        fbb.put(arr)
        fbb.position(0)
        return fbb
    }

}
