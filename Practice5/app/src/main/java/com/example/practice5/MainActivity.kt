package com.example.practice5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var editText = findViewById<EditText>(R.id.editTextNumber)
        var button = findViewById<Button>(R.id.button)

        var randnum = Random.nextInt(1, 10000)
        Toast.makeText(this, "Recordar:" + randnum.toString(), Toast.LENGTH_LONG).show()

        button.setOnClickListener{
            if(editText.text.toString().toInt() == randnum){
                Toast.makeText(this, "Bien", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this, "Mal", Toast.LENGTH_LONG).show()
            }
        }
    }
}