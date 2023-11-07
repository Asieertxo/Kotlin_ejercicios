package com.example.psp_practice01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val startStop = findViewById<Button>(R.id.button)
        val tempHour = findViewById<EditText>(R.id.tempHour)
        val tempMin = findViewById<EditText>(R.id.tempMin)
        val tempSeg = findViewById<EditText>(R.id.tempSeg)
        val intent = Intent(this, MyService::class.java)


        startStop.setOnClickListener(){
            var totalTime = calcTotalTime(tempHour.toString().toInt(), tempMin.toString().toInt(), tempSeg.toString().toInt())
            if (startStop.text == "start"){
                intent.putExtra("time", totalTime)
                startService(intent)
                startStop.text = "stop"
            }else{
                stopService(intent)
                startStop.text = "start"
            }
        }
    }

    fun calcTotalTime(hour: Int, min: Int, seg: Int): Int{
        return  (hour * 3600) + (min * 60) + seg
    }
}