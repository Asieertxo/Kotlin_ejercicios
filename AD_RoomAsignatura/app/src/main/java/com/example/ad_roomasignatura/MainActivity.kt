package com.example.ad_roomasignatura

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import android.content.Intent
import android.widget.ArrayAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val tv_asignaturas = findViewById<TextView>(R.id.tv_asignaturas)
        val et_asigName = findViewById<TextInputEditText>(R.id.et_asigName)
        val et_asigTeacher = findViewById<AutoCompleteTextView>(R.id.et_asigTeacher)
        val til_asigName = findViewById<TextInputLayout>(R.id.til_asigName)
        val til_asigTeacher = findViewById<TextInputLayout>(R.id.til_asigTeacher)
        val btn_asigInsert = findViewById<Button>(R.id.btn_asigInsert)
        val btn_asigDelete = findViewById<Button>(R.id.btn_asigDelete)
        val btn_asigAsignarProfe = findViewById<Button>(R.id.btn_asigAsignarProfe)

        val tv_profesores = findViewById<TextView>(R.id.tv_profesores)
        val et_teachName = findViewById<TextInputEditText>(R.id.et_teachName)
        val et_teachSurname = findViewById<TextInputEditText>(R.id.et_teachSurname)
        val et_teachDate = findViewById<TextInputEditText>(R.id.et_teachDate)
        val til_teachName = findViewById<TextInputLayout>(R.id.til_teachName)
        val til_teachSurname = findViewById<TextInputLayout>(R.id.til_teachSurname)
        val til_teachDate = findViewById<TextInputLayout>(R.id.til_teachDate)
        val btn_teachInsert = findViewById<Button>(R.id.btn_teachInsert)
        val btn_teachDelete = findViewById<Button>(R.id.btn_teachDelete)


        //creamos la bbdd
        database = Room.databaseBuilder(
            application, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .allowMainThreadQueries()
            .build()

        //mostramos las asignaturas
        showAsignaturas(database, tv_asignaturas)

        //mostramos los profesores
        showProfesores(database, tv_profesores)



        val profesores = database.profesorDao.getAllProfesores()
        val profesoresList: MutableList<String> = mutableListOf()
        profesores.forEach { profesor ->
            profesoresList.add(profesor.name)
        }


        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            profesoresList.toList()
        )

        et_asigTeacher.setAdapter(adapter)
        var selectedItem : String =""

        et_asigTeacher.setOnItemClickListener { parent, _, position, _ ->
            selectedItem = profesoresList.toList()[position]
            // Realiza alguna acción con el elemento seleccionado
            Toast.makeText(this, profesoresList[selectedItem].toString(), Toast.LENGTH_SHORT).show()

        }







        //ASIGNATURA

        btn_asigInsert.setOnClickListener(){
            val newAsinName = et_asigName.text.toString()
            val newAsinTeacher = et_asigTeacher.text.toString()
            til_asigName.error = null
            til_asigTeacher.error = null

            if (newAsinName.trim().isNotEmpty()) {
                if (newAsinTeacher.trim().isNotEmpty()) {

                    val newAsignatura = AsignaturaEntity(name = newAsinName, teacher = newAsinTeacher)
                    database.asignaturaDao.insert(newAsignatura)
                    Toast.makeText(this, "Asignatura insertada", Toast.LENGTH_SHORT).show()
                    et_asigName.setText("")
                    et_asigTeacher.setText("")
                    showAsignaturas(database, tv_asignaturas)

                }else{
                    til_asigTeacher.error = "Rellena el campo"
                    til_asigTeacher.requestFocus()
                }
            }else{
                til_asigName.error = "Rellena el campo"
                til_asigName.requestFocus()
            }
        }

        btn_asigDelete.setOnClickListener(){
            val asigName = et_asigName.text.toString()

            if (asigName.trim().isNotEmpty()) {

                val asignatura: List<AsignaturaEntity> = database.asignaturaDao.getAsignaturaByName(asigName)
                if (asignatura.size > 0) {
                    database.asignaturaDao.delete(asignatura.first())
                    Toast.makeText(this, "Asignatura Borrada", Toast.LENGTH_SHORT).show()
                    showAsignaturas(database, tv_asignaturas)
                }
            }else{
                til_teachDate.error = "Rellena el campo"
                til_teachDate.requestFocus()
            }
        }

        btn_asigAsignarProfe.setOnClickListener(){
            val asigName = et_asigName.text.toString()
            val newAsinTeacher = et_asigTeacher.text.toString()

            if (asigName.trim().isNotEmpty()) {
                if (newAsinTeacher.trim().isNotEmpty()) {

                    val asignatura: List<AsignaturaEntity> = database.asignaturaDao.getAsignaturaByName(asigName)
                    if (asignatura.size > 0) {
                        asignatura.first().teacher = newAsinTeacher
                        database.asignaturaDao.update(asignatura.first())
                        Toast.makeText(this, "Profesor asignado", Toast.LENGTH_SHORT).show()
                        showAsignaturas(database, tv_asignaturas)
                    }

                }else{
                    til_asigTeacher.error = "Rellena el campo"
                    til_asigTeacher.requestFocus()
                }
            }else{
                til_asigName.error = "Rellena el campo"
                til_asigName.requestFocus()
            }
        }



        //PROFESOR

        btn_teachInsert.setOnClickListener(){
            val newTeachName = et_teachName.text.toString()
            val newTeachSurname = et_teachSurname.text.toString()
            val newTeachDate = et_teachDate.text.toString()
            til_teachName.error = null
            til_teachSurname.error = null
            til_teachDate.error = null

            if (newTeachName.trim().isNotEmpty()) {
                if (newTeachSurname.trim().isNotEmpty()) {
                    if (newTeachDate.trim().isNotEmpty()) {

                        val newTeacher = ProfesorEntity(name = newTeachName, surname = newTeachSurname, date = newTeachDate)
                        database.profesorDao.insert(newTeacher)
                        Toast.makeText(this, "Profesor insertado", Toast.LENGTH_SHORT).show()
                        showProfesores(database, tv_profesores)

                    }else{
                        til_teachDate.error = "Rellena el campo"
                        til_teachDate.requestFocus()
                    }
                }else{
                    til_teachSurname.error = "Rellena el campo"
                    til_teachSurname.requestFocus()
                }
            }else{
                til_teachName.error = "Rellena el campo"
                til_teachName.requestFocus()
            }
        }

        btn_teachDelete.setOnClickListener(){
            val techName = et_teachName.text.toString()

            if (techName.trim().isNotEmpty()) {
                val asignatura: List<AsignaturaEntity> = database.asignaturaDao.getAsignaturaByTeacher(techName)
                if (asignatura.size <= 0) {
                    val profesor: List<ProfesorEntity> = database.profesorDao.getProfesorByName(techName)
                    if (profesor.size > 0) {
                        database.profesorDao.delete(profesor.first())
                        Toast.makeText(this, "Profesor borrado", Toast.LENGTH_SHORT).show()
                        showProfesores(database, tv_profesores)
                    }
                }else {
                    til_teachName.error = "Hay asignaturas con este profesor"
                    til_teachName.requestFocus()
                }
            }else{
                til_teachName.error = "Rellena el campo"
                til_teachName.requestFocus()
            }

        }
    }


    fun showAsignaturas(database: AppDatabase, tv_asignaturas: TextView) {
        tv_asignaturas.text = ""
        val asignaturas = this.database.asignaturaDao.getAllAsignaturas()
        asignaturas.forEach { asignatura ->
            tv_asignaturas.append("${asignatura.name} -> Profesor: ${asignatura.teacher}\n")
        }
    }

    fun showProfesores(database: AppDatabase, tv_profesores: TextView){
        tv_profesores.text = ""
        val profesores = database.profesorDao.getAllProfesores()
        profesores.forEach { profesor ->
            tv_profesores.append("${profesor.name} ${profesor.surname} -> Fecha: ${profesor.date}\n")
        }
    }
}