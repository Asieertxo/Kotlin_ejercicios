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
        val tempSeg = findViewById<EditText>(R.id.tempSeg)
        val intent = Intent(this, MyService::class.java)


        startStop.setOnClickListener(){
            if (startStop.text == "start"){
                val time: Int = tempSeg.text.toString().toInt()
                intent.putExtra("time", time)
                startService(intent)
                startStop.text = "stop"
            }else{
                stopService(intent)
                startStop.text = "start"
            }
        }
    }
}