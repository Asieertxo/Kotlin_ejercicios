package com.txurdinaga.serversocket

import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.ServerSocket
import java.net.Socket

class MainActivity : AppCompatActivity() {
    private lateinit var serverSocket: ServerSocket
    private lateinit var messageTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        messageTextView = findViewById(R.id.messageTextView)

        // Configurar la política de red (esto no se debe hacer en el hilo principal en una aplicación real)
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .permitNetwork()
                .build()
        )

        // Iniciar el servidor en un hilo separado
        Thread {
            try {
                serverSocket = ServerSocket(51744)
                Log.d("ServerSocket", "Server socket activo en el puerto 51744")

                //for(i in 1..3) {
                while (true){
                    val clientSocket: Socket = serverSocket.accept()
                    Log.d("ServerSocket", "Cliente conectado")

                    val reader = BufferedReader(InputStreamReader(clientSocket.getInputStream()))
                    val message = reader.readLine()
                    var respuesta_esPrimo = "Error al calcularlo"

                    runOnUiThread {
                        messageTextView.text = "Mensaje recibido: $message"
                    }
                    respuesta_esPrimo = esPrimo(message.toInt())

                    // Enviar respuesta al cliente (Hola)
                    val writer = BufferedWriter(OutputStreamWriter(clientSocket.getOutputStream()))
                    writer.write("El numero $message $respuesta_esPrimo\n")
                    writer.flush()
                    writer.close()


                    reader.close()
                    clientSocket.close()
                    Log.d("ServerSocket", "Cliente desconectado")
                }
            } catch (e: IOException) {
                e.printStackTrace()
                Log.e("ServerSocket", "Error en el servidor: ${e.message}")
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            serverSocket.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    fun esPrimo(numero: Int): String {
        if (numero <= 1) {
            return "El número no es primo"
        }

        for (i in 2 until numero) {
            if (numero % i == 0) {
                return "El número no es primo"
            }
        }

        return "El número es primo"
    }
}
