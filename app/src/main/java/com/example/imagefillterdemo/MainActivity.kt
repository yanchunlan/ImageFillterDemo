package com.example.imagefillterdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button1.setOnClickListener { imageGlSurfaceView.setFilter(ImageFilter.NONE) }
        button2.setOnClickListener { imageGlSurfaceView.setFilter(ImageFilter.GRAY) }
        button3.setOnClickListener { imageGlSurfaceView.setFilter(ImageFilter.COOL) }
        button4.setOnClickListener { imageGlSurfaceView.setFilter(ImageFilter.WARM) }

    }
}
