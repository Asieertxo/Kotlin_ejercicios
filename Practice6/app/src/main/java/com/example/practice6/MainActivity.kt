package com.example.practice6

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import java.io.BufferedReader

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.editTextText)
        val boton = findViewById<Button>(R.id.button)
        var intent1 = Intent(this, MainActivity2::class.java)

        boton.setOnClickListener {
            var text = editText.text
            intent1.putExtra("name", text.toString())
            startActivity(intent1)
        }

    }
}