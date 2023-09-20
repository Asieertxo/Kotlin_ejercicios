package com.example.practice1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button).setOnClickListener{
            try {
                if (findViewById<Button>(R.id.button).text == getString(R.string.comprobar)) {
                    val number = findViewById<EditText>(R.id.editText).text.toString().toInt();

                    if (!isPrime(number)) {
                        findViewById<TextView>(R.id.textView).text = getString(R.string.primo)
                    } else {
                        findViewById<TextView>(R.id.textView).text = getString(R.string.noprimo)
                    }
                    findViewById<Button>(R.id.button).text = getString(R.string.reiniciar)

                } else if (findViewById<Button>(R.id.button).text == getString(R.string.reiniciar)) {
                    findViewById<TextView>(R.id.textView).text = getString(R.string.insert)
                    findViewById<TextView>(R.id.editText).text = ""
                    findViewById<Button>(R.id.button).text = getString(R.string.comprobar)
                }

            }catch (e: NumberFormatException){
                findViewById<TextView>(R.id.textView).text = getString(R.string.insert)
                findViewById<TextView>(R.id.editText).text = ""
                findViewById<Button>(R.id.button).text = getString(R.string.comprobar)
            }
        }
    }

    private fun isPrime(num:Int): Boolean {
        var flag = false;
        for (i in 2..num / 2) {
            if (num % i == 0) {
                flag = true
                break
            }
        }
        return flag
    }
}