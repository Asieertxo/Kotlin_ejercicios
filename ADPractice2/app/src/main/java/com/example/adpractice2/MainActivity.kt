package com.example.adpractice2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Firebase.firestore
        val texto_id = findViewById<EditText>(R.id.text_id).text
        val texto_descripcion = findViewById<EditText>(R.id.text_descripcion).text
        val texto_precio = findViewById<EditText>(R.id.text_precio).text

        val boton_alta = findViewById<Button>(R.id.boton_alta)
        val boton_baja = findViewById<Button>(R.id.boton_baja)
        val boton_listar = findViewById<Button>(R.id.boton_listar)
        val boton_modificar = findViewById<Button>(R.id.boton_modificar)
        val boton_consultaCodigo = findViewById<Button>(R.id.boton_consultaCodigo)
        val boton_consultaDescripcion = findViewById<Button>(R.id.boton_consultaDescripcion)

        val lista = findViewById<TextView>(R.id.lista)
        var productos = ""

        fun getProducts() {
            lista.text = ""
            productos = ""
            db.collection("productos")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        productos = productos + "\n" + "Producto: " + document.data.get("descripcion").toString() + " Precio: " + document.data.get("precio").toString()
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
                .whereEqualTo("id", texto_id.toString().toLong())
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        productos = "Producto: " + document.data.get("descripcion").toString() + " Precio: " + document.data.get("precio").toString()
                        lista.text = productos
                    }
                }
                .addOnFailureListener { exception ->
                    Log.i("LEER", "Error getting documents.", exception)
                }
        }

        boton_consultaDescripcion.setOnClickListener {
            db.collection("productos")
                .whereEqualTo("descripcion", texto_descripcion.toString())
                .get()
                .addOnSuccessListener { result ->
                    for (document in result){
                        productos = "Producto: " + document.data.get("descripcion").toString() + " Precio: " + document.data.get("precio").toString()
                        lista.text = productos
                    }
                }
        }


        boton_alta.setOnClickListener {
            var newProduct = hashMapOf(
                "id" to texto_id.toString().toInt(),
                "descripcion" to texto_descripcion.toString(),
                "precio" to texto_precio.toString().toInt()
            )

            db.collection("productos")
                .add(newProduct)
                .addOnCompleteListener {
                    Toast.makeText(this, "producto_subido", Toast.LENGTH_LONG).show()
                    getProducts()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al subir", Toast.LENGTH_LONG).show()
                }

        }

        boton_baja.setOnClickListener{
            db.collection("productos")
                .whereEqualTo("id", texto_id.toString().toInt())
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        db.collection("productos").document(document.id)
                            .delete()
                            .addOnCompleteListener {
                                Toast.makeText(this, "Producto borrado", Toast.LENGTH_LONG).show()
                                getProducts()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Error al borrar", Toast.LENGTH_LONG).show()
                            }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.i("LEER", "Error getting documents.", exception)
                }

            db.collection("productos").document("${texto_id.toString().toInt()}")
                .delete()
                .addOnCompleteListener {
                    Toast.makeText(this, "Producto borrado", Toast.LENGTH_LONG).show()
                    getProducts()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error al borrar", Toast.LENGTH_LONG).show()
                }
        }

        boton_modificar.setOnClickListener{
            var newProduct = hashMapOf(
                "id" to texto_id.toString().toInt(),
                "descripcion" to texto_descripcion.toString(),
                "precio" to texto_precio.toString().toInt()
            )

            db.collection("productos")
                .whereEqualTo("id", texto_id.toString().toInt())
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        db.collection("productos")
                            .document(document.id)
                                .update("descripcion", texto_descripcion.toString(), "precio", texto_precio.toString().toInt())
                                .addOnCompleteListener {
                                    Toast.makeText(this, "Producto actualizado", Toast.LENGTH_LONG).show()
                                    getProducts()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(this, "Error al actualizar", Toast.LENGTH_LONG).show()
                                }
                    }
                }
        }


    }
}