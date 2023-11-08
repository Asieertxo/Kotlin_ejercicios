package com.example.practice3

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var paises = arrayOf("Argentina", "Chile", "Paraguay", "Bolibia", "Peru", "Ecuador", "Brasil", "Colombia", "Venezuela", "Paraguay")
        var poblacion = arrayOf("40.000.000", "17.000.000", "6.500.000", "20.000.000", "50.000.000", "12.000.000", "49.000.000", "30.000.000", "20.000.000", "10.000.000")

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, paises)
        val lista = findViewById<ListView>(R.id.lista)
        lista.adapter = adapter

        lista.onItemClickListener = AdapterView.OnItemClickListener{ parent, view, position, id ->
            val texto = findViewById<TextView>(R.id.textView)
            texto.text =  poblacion[position]
            val toast = Toast.makeText(this, poblacion[position], Toast.LENGTH_LONG)
            toast.show()
        }

    }
}