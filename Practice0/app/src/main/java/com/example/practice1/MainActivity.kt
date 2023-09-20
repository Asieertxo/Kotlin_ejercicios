package com.example.practice1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener{
            if(findViewById<TextView>(R.id.textView).text == "Pulsa el boton"){
                findViewById<TextView>(R.id.textView).text = "Hola mundo"
            }else{
                findViewById<TextView>(R.id.textView).text = "Pulsa el boton"
            }
        }
    }
}