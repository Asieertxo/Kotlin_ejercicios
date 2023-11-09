package com.example.psp_practice01

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    var totalTime : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val startStop = findViewById<Button>(R.id.button)
        val tempHour = findViewById<EditText>(R.id.tempHour)
        val tempMin = findViewById<EditText>(R.id.tempMin)
        val tempSeg = findViewById<EditText>(R.id.tempSeg)
        val intent = Intent(this, MyService::class.java)

        //Poner las preferencias
        val prefs : SharedPreferences = getSharedPreferences("time",Context.MODE_PRIVATE)
        val segundosPrefs = prefs.getInt("segundos", 0)
        val resultado = segundosAHorasMinutosSegundos(segundosPrefs.toString().toInt())
        val (horas, minutos, segundos) = resultado
        tempHour.setText(horas.toString())
        tempMin.setText(minutos.toString())
        tempSeg.setText(segundos.toString())

        //BroadCast del servicio
        val temporizador = IntentFilter("broadcast_temporizador")
        registerReceiver(broadcastReceiver, temporizador)

        startStop.setOnClickListener(){
            if(tempHour.text.isNotEmpty()) {
                totalTime = calcTotalTime(
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
            val startStop = findViewById<Button>(R.id.button)
            val progressBar = findViewById<ProgressBar>(R.id.progressBar)

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

                //Barra de Progreso

                progressBar.progress = ((segundosRestantes.toString().toInt() * 100) / totalTime).toInt()

                //Cuando termine el temporizador que ponga las cosas como el principio
                if(intent.getBooleanExtra("terminado", false)){
                    tempHour.isEnabled = true
                    tempMin.isEnabled = true
                    tempSeg.isEnabled = true
                    startStop.text = "start"
                }
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