package com.example.practice4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<ImageButton>(R.id.imagen).setOnClickListener{
            if(findViewById<TextView>(R.id.texto).text == "Llamar"){
                findViewById<TextView>(R.id.texto).text = "Llamando..."
            }else{
                findViewById<TextView>(R.id.texto).text = "Llamar"
            }
        }
    }
}