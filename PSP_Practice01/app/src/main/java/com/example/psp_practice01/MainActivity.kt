package com.example.psp_practice01

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val startStop = findViewById<Button>(R.id.button)
        val tempHour = findViewById<EditText>(R.id.tempHour)
        val tempMin = findViewById<EditText>(R.id.tempMin)
        val tempSeg = findViewById<EditText>(R.id.tempSeg)
        val intent = Intent(this, MyService::class.java)


        val temporizador = IntentFilter("broadcast_temporizador")
        registerReceiver(broadcastReceiver, temporizador)
        val tempTerminado = IntentFilter("broadcast_temp_terminado")
        registerReceiver(broadcastReceiver, tempTerminado)

        startStop.setOnClickListener(){
            if(tempHour.text.isNotEmpty()) {
                var totalTime = calcTotalTime(
                    tempHour.text.toString().toInt(),
                    tempMin.text.toString().toInt(),
                    tempSeg.text.toString().toInt()
                )
                if (startStop.text == "start") {
                    intent.putExtra("time", totalTime)
                    startService(intent)
                    startStop.text = "stop"
                } else {
                    stopService(intent)
                    startStop.text = "start"
                }
            }
        }
    }

    fun calcTotalTime(hour: Int, min: Int, seg: Int): Int{
        return  (hour * 3600) + (min * 60) + seg
    }

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val tempHour = findViewById<EditText>(R.id.tempHour)
            val tempMin = findViewById<EditText>(R.id.tempMin)
            val tempSeg = findViewById<EditText>(R.id.tempSeg)
            if (intent?.action == "broadcast_temporizador") {
                val segundosRestantes = intent.getStringExtra("resultado")

                val resultado = segundosAHorasMinutosSegundos(segundosRestantes.toString().toInt())
                val (horas, minutos, segundos) = resultado
                tempHour.setText(horas.toString())
                tempMin.setText(minutos.toString())
                tempSeg.setText(segundos.toString())
                tempHour.isEnabled = false
                tempMin.isEnabled = false
                tempSeg.isEnabled = false
            }else if(intent?.action == "broadcast_temp_terminado"){
                tempHour.isEnabled = true
                tempMin.isEnabled = true
                tempSeg.isEnabled = true
            }
        }
    }

    fun segundosAHorasMinutosSegundos(segundos: Int): Triple<Int, Int, Int> {
        val horas = segundos / 3600
        val minutos = (segundos % 3600) / 60
        val segundosRestantes = segundos % 60

        return Triple(horas, minutos, segundosRestantes)
    }
}