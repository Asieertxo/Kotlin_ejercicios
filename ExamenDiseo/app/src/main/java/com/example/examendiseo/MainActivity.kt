package com.example.examendiseo

import android.app.AlertDialog
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Ejercicio4
        val orientation = resources.configuration.orientation

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main_portrait)
        } else {
            setContentView(R.layout.activity_main)

        }

        //Ejercicio1
        var comida = arrayOf("Buey Mishi Kobe", "Pez espada", "Queso cabrales", "Queso Manchego La Pastora", "Algas Konbu", "Cuajada de juadias", "Salsa de soja baja en sodio", "Postre de merengue Pavlova")
        var precio = arrayOf("97,00", "31,00", "21,00", "38,00", "6,00", "23,25", "15,50", "17,45")

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, comida)
        val lista = findViewById<ListView>(R.id.lista)
        lista.adapter = adapter

        lista.onItemClickListener = AdapterView.OnItemClickListener{ parent, view, position, id ->
            val texto = findViewById<TextView>(R.id.textView)
            texto.text =  precio[position]
            val toast = Toast.makeText(this, precio[position], Toast.LENGTH_LONG)
            toast.show()
        }

        //Ejercicio2
        //No tiene codigo, es por XML


        //Ejercicio3
        val boton2 = findViewById<Button>(R.id.boton2)
        boton2.setOnClickListener(){
            boton2.setBackgroundColor(resources.getColor(R.color.colorPressed))
        }


        //Ejericicio5
        val botonCerrar = findViewById<Button>(R.id.buttonCerrar)
        botonCerrar.setOnClickListener {
            mostrarDialogoDeConfirmacion()
        }



        //Ejercicio6
        val imageView = findViewById<ImageView>(R.id.imageView)
        val popupMenu = PopupMenu(this, imageView)
        popupMenu.menu.add("Opción 111")
        popupMenu.menu.add("Opción 222")
        popupMenu.menu.add("Opción 333")

        imageView.setOnClickListener { view ->
            popupMenu.show()
        }

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.title) {
                "Opción 111" -> {
                    true
                }
                "Opción 222" -> {
                    // Lógica para la opción 2
                    true
                }
                "Opción 333" -> {
                    // Lógica para la opción 3
                    true
                }
                else -> false
            }
        }
    }


    //
    private fun mostrarDialogoDeConfirmacion() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmar cierre de aplicación")
        builder.setMessage("¿Estás seguro de que deseas cerrar la aplicación?")

        builder.setPositiveButton("Sí") { dialog, which ->
            // Cierra la aplicación
            finish()
        }

        builder.setNegativeButton("No") { dialog, which ->
            // No hacer nada, simplemente cierra el cuadro de diálogo
        }

        val dialog = builder.create()
        dialog.show()
    }

}
