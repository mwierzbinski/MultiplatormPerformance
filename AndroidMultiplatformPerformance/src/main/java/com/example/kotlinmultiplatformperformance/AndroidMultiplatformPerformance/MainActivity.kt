package com.example.kotlinmultiplatformperformance.AndroidMultiplatformPerformance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotlinmultiplatformperformance.common.Greeting
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime

@ExperimentalTime
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.text_view)
        tv.text = "start computing"

        Greeting().greeting { tv.text = it }
    }
}
