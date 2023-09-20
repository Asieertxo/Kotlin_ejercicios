package com.example.practice2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.boton).setOnClickListener{
            val num1 = findViewById<EditText>(R.id.num1).text.toString().toInt();
            val num2 = findViewById<EditText>(R.id.num2).text.toString().toInt();
            var resultado = 0;
            val suma: RadioButton = findViewById<RadioButton>(R.id.suma)

            if(findViewById<Button>(R.id.boton).text == getString(R.string.operar)){
                if(suma.isChecked){
                    resultado = num1 + num2;
                }else{
                    resultado = num1 - num2;
                }
                findViewById<EditText>(R.id.num1).isVisible = false;
                findViewById<EditText>(R.id.num2).isVisible = false;
                findViewById<RadioButton>(R.id.suma).isVisible = false
                findViewById<RadioButton>(R.id.resta).isVisible = false
                findViewById<TextView>(R.id.resultado).isVisible = true
                findViewById<TextView>(R.id.resultado).text = resultado.toString()
                findViewById<Button>(R.id.boton).text = getString(R.string.reiniciar)
                findViewById<TextView>(R.id.mensaje).text = getString(R.string.resultado)
            }else if(findViewById<Button>(R.id.boton).text == getString(R.string.reiniciar)){
                findViewById<EditText>(R.id.num1).isVisible = true;
                findViewById<EditText>(R.id.num2).isVisible = true;
                findViewById<RadioButton>(R.id.suma).isVisible = true
                findViewById<RadioButton>(R.id.resta).isVisible = true
                findViewById<TextView>(R.id.resultado).isVisible = false
                findViewById<Button>(R.id.boton).text = getString(R.string.operar)
                findViewById<TextView>(R.id.mensaje).text = getString(R.string.introducir)
            }
        }
    }
}