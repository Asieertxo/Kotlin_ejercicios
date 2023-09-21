package com.example.practice8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val num1 = findViewById<EditText>(R.id.num1)
        val num2 = findViewById<EditText>(R.id.num2)
        val boton = findViewById<Button>(R.id.boton)
        val spinner = findViewById<Spinner>(R.id.spinner)
        val texto = findViewById<TextView>(R.id.textView)
        var resultado: Double = 0.0
        val lista = arrayOf("sumar", "restar", "multiplicar", "dividir")
        val adaptador1 = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lista)
        spinner.adapter = adaptador1

        boton.setOnClickListener {
            when (spinner.selectedItem.toString()) {
                "sumar" -> resultado =
                    num1.text.toString().toDouble() + num2.text.toString().toDouble()

                "restar" -> resultado =
                    num1.text.toString().toDouble() - num2.text.toString().toDouble()

                "multiplicar" -> resultado =
                    num1.text.toString().toDouble() * num2.text.toString().toDouble()

                "dividir" -> resultado =
                    num1.text.toString().toDouble() / num2.text.toString().toDouble()
            }

            texto.text = "Resultado: " + resultado.toString()
        }
    }
}