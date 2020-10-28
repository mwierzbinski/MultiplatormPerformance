package com.example.kotlinmultiplatformperformance.AndroidMultiplatformPerformance

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlinmultiplatformperformance.common.Greeting
import kotlin.time.ExperimentalTime

@ExperimentalTime
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)
        tv.text = "start computing"

        Greeting().greeting {
            Log.d("AndroidTestWoo", it)
            tv.text = it
        }
    }
}
