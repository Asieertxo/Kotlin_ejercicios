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

        var texto = findViewById<TextView>(R.id.texto)

        val changeText = { texto: TextView -> if (texto.text == "Llamar") texto.text = "Llamando..." else texto.text = "Llamar"}
        findViewById<ImageButton>(R.id.imagen).setOnClickListener{changeText(texto)}
    }
}