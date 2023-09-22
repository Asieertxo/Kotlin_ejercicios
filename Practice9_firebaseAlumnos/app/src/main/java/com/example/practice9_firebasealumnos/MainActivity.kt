package com.example.practice9_firebasealumnos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Firebase.firestore


        findViewById<Button>(R.id.boton).setOnClickListener {
            db.collection("Alumnos")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d("LEER", "${document.id} => ${document.data}")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("LEER", "Error getting documents.", exception)
                }

            val kk = "hola"
        }
    }
}