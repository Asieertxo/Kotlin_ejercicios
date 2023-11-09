package com.example.psp_practice01

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyService : Service() {

    val scope = CoroutineScope(Dispatchers.Default)
    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        var time = intent.getIntExtra("time", 0)
        scope.launch {
            while (time >= 0) {
                val intent = Intent("broadcast_temporizador")
                intent.putExtra("resultado", time.toString())
                //preferencias
                val editor : SharedPreferences = getSharedPreferences("time", Context.MODE_PRIVATE)
                editor.edit().putInt("segundos", time).apply()

                if(time <= 0){
                    intent.putExtra("terminado", true)
                }
                sendBroadcast(intent)
                time--
                delay(1000)
            }
            val mediaPlayer = MediaPlayer.create(applicationContext, R.raw.nanomusic)
            mediaPlayer.start()
        }
        return START_STICKY
    }

    override fun onDestroy() {
        scope.cancel()
    }
}