package com.example.imagefillterdemo

// 定义过滤器，用于对图片操作
enum class ImageFilter private constructor(val type: Int, private val data: FloatArray) {
    NONE(0, floatArrayOf(0.0f, 0.0f, 0.0f)), // 不做处理
    GRAY(1, floatArrayOf(0.299f, 0.587f, 0.114f)), // 黑白效果
    COOL(2, floatArrayOf(0.0f, 0.0f, 0.1f)), // 冷色调
    WARM(2, floatArrayOf(0.1f, 0.1f, 0.0f));// 暖色调

    fun data(): FloatArray {
        return data
    }
}