package com.txurdinaga.cliente

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.Socket

class MainActivity : AppCompatActivity() {
    private lateinit var sendMessageButton: Button
    private lateinit var messageEditText: EditText
    private lateinit var respuestaTextView: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sendMessageButton = findViewById(R.id.sendMessageButton)
        messageEditText = findViewById(R.id.messageEditText)
        respuestaTextView = findViewById(R.id.respuestaTextView)

        // Configurar la política de red (esto no se debe hacer en el hilo principal en una aplicación real)
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .permitNetwork()
                .build()
        )

        sendMessageButton.setOnClickListener {
            val message = messageEditText.text.toString()
            sendMessageToServer(message)
        }
    }

    private fun sendMessageToServer(message: String) {
        Thread {
            try {
                val socket = Socket("localhost", 51744)
                val outputStream: OutputStream = socket.getOutputStream()

                // Enviar mensaje al servidor
                outputStream.write(message.toByteArray())
                Log.d("ClientSocket", "Mensaje enviado")

                // Cerrar el outputStream para indicar al servidor que no se enviarán más datos
                socket.shutdownOutput()

                // Recibir respuesta del servidor
                val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
                val response = reader.readLine()
                respuestaTextView.text = response.toString()

                Log.d("ClientSocket", "Respuesta del servidor: $response")

                reader.close()
                socket.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }
}
