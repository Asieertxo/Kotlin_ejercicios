package com.example.practice7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val boton = findViewById<Button>(R.id.button)
        val texto = findViewById<EditText>(R.id.editTextText)

        var intent1 = Intent(this, MainActivity2::class.java)

        boton.setOnClickListener {
            if(texto.text.toString() == "abc123"){
                var text = texto.text.toString()
                intent1.putExtra("clave", text)
                startActivity(intent1)
            }else{
                Toast.makeText(this, "Esta no es la clave", Toast.LENGTH_LONG).show()
            }
        }

    }
}