package com.example.adpractice0

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Firebase.firestore
        var alumnos = ""

        findViewById<Button>(R.id.boton).setOnClickListener {
            alumnos = ""
            db.collection("Alumnos")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        Log.d("LEER", "${document.id} => ${document.data}")
                        alumnos = alumnos + "\n" + "Nombre: " + document.data.get("nombre").toString() + " Ciudad: " + document.data.get("ciudad").toString()
                        findViewById<TextView>(R.id.textView1).text = alumnos
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("LEER", "Error getting documents.", exception)
                }
        }
    }
}