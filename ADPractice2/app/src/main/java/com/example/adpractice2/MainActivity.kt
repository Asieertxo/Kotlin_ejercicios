package com.example.adpractice2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Firebase.firestore
        val texto_id = findViewById<EditText>(R.id.text_id)
        val texto_descripcion = findViewById<EditText>(R.id.text_descripcion)
        val texto_precio = findViewById<EditText>(R.id.text_precio)

        val boton_alta = findViewById<Button>(R.id.boton_alta)
        val boton_baja = findViewById<Button>(R.id.boton_baja)
        val boton_listar = findViewById<Button>(R.id.boton_listar)
        val boton_modificar = findViewById<Button>(R.id.boton_modificar)
        val boton_consultaCodigo = findViewById<Button>(R.id.boton_consultaCodigo)
        val boton_consultaDescripcion = findViewById<Button>(R.id.boton_consultaDescripcion)

        val lista = findViewById<TextView>(R.id.lista)
        var productos = ""

        fun getProducts() {
            db.collection("productos")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        productos =
                            productos + "\n" + "Producto: " + document.data.get("descripcion")
                                .toString() + " Precio: " + document.data.get("precio").toString()
                        lista.text = productos
                    }
                }
                .addOnFailureListener { exception ->
                    Log.i("LEER", "Error getting documents.", exception)
                }
        }

        getProducts()//que carguen los datos al arrancar

        boton_listar.setOnClickListener {
            getProducts()
        }

        boton_consultaCodigo.setOnClickListener {
            db.collection("productos")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result){
                        if(document.data.get("id") == texto_id.text.toString().toLong()) {
                            productos = "Producto: " + document.data.get("descripcion").toString() + " Precio: " + document.data.get("precio").toString()
                            lista.text = productos
                        }
                    }
                }
        }

        boton_consultaDescripcion.setOnClickListener {
            db.collection("productos")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result){
                        if(document.data.get("descripcion").toString().toLowerCase() == texto_descripcion.text.toString().toLowerCase()) {
                            productos = "Producto: " + document.data.get("descripcion").toString() + " Precio: " + document.data.get("precio").toString()
                            lista.text = productos
                        }
                    }
                }
        }


        boton_alta.setOnClickListener {
            var newProduct = hashMapOf(
                "id" to texto_id.text,
                "descripcion" to texto_descripcion.text.toString(),
                "precio" to texto_precio.text
            )

            db.collection("productos").document("$texto_id")
                .set(newProduct)
                .addOnCompleteListener {
                    Toast.makeText(this, "Producto subido", Toast.LENGTH_LONG).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al subir", Toast.LENGTH_LONG).show()
                }

        }
    }
}