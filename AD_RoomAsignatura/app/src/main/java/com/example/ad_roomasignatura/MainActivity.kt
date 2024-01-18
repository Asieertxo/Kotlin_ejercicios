package com.example.ad_roomasignatura

import android.database.sqlite.SQLiteException
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
import android.widget.ArrayAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase
    var selectedTeacher : String =""

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


        //Dropdown de profesores y gestion
        setTeacherDropdown()









        //ASIGNATURA

        btn_asigInsert.setOnClickListener(){
            val newAsinName = et_asigName.text.toString()
            til_asigName.error = null
            til_asigTeacher.error = null

            if (newAsinName.trim().isNotEmpty()) {
                var newAsignatura = AsignaturaEntity(name = newAsinName, teacher = selectedTeacher)
                if(selectedTeacher == "" || selectedTeacher == "Sin Profesor"){
                    newAsignatura = AsignaturaEntity(name = newAsinName, teacher = null)
                }

                try {
                    database.asignaturaDao.insert(newAsignatura)
                    Toast.makeText(this, "Asignatura insertada", Toast.LENGTH_SHORT).show()
                    et_asigName.setText("")
                    et_asigTeacher.setText("")
                    showAsignaturas(database, tv_asignaturas)
                }catch (e: SQLiteException){
                    Log.i("error", e.toString())
                    Toast.makeText(this, "Puede que ya exista la asignatura", Toast.LENGTH_SHORT).show()
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

            asignarProfe(asigName, newAsinTeacher)
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

                        try {
                            val newTeacher = ProfesorEntity(name = newTeachName, surname = newTeachSurname, date = newTeachDate)
                            database.profesorDao.insert(newTeacher)
                            Toast.makeText(this, "Profesor insertado", Toast.LENGTH_SHORT).show()
                            showProfesores(database, tv_profesores)
                            setTeacherDropdown()
                        }catch (e: SQLiteException){
                            Toast.makeText(this, "Puede que ya exista el profesor", Toast.LENGTH_SHORT).show()
                        }



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
                //comprobamos si hay asignaturas con este profesor y lo desasignamos
                val asignaturas: List<AsignaturaEntity> = database.asignaturaDao.getAsignaturaByTeacher(techName)
                if (asignaturas.size > 0) {
                    asignaturas.forEach { asignatura ->
                        asignarProfe(asignatura.name, "")
                    }
                }

                val profesor: List<ProfesorEntity> = database.profesorDao.getProfesorByName(techName)
                if (profesor.size > 0) {
                    database.profesorDao.delete(profesor.first())
                    Toast.makeText(this, "Profesor borrado", Toast.LENGTH_SHORT).show()
                    showProfesores(database, tv_profesores)
                    setTeacherDropdown()
                }
            }else{
                til_teachName.error = "Rellena el campo"
                til_teachName.requestFocus()
            }

        }
    }


    fun showAsignaturas(database: AppDatabase, tv_asignaturas: TextView) {
        tv_asignaturas.text = ""
        val asignaturas = this.database.asignaturaProfesorDao.getAllAsignaturasProfesor()
        Log.i("kkk", asignaturas.toString())
        asignaturas.forEach { asignatura ->
            if(asignatura.profesor_name.isNullOrEmpty()){
                tv_asignaturas.append("${asignatura.asignatura_name} -> Profesor: Sin Profesor\n")
            }else {
                tv_asignaturas.append("${asignatura.asignatura_name} -> Profesor: ${asignatura.profesor_name} ${asignatura.profesor_surname}\n")
            }
        }
    }

    fun showProfesores(database: AppDatabase, tv_profesores: TextView){
        tv_profesores.text = ""
        val profesores = database.profesorDao.getAllProfesores()
        profesores.forEach { profesor ->
            tv_profesores.append("${profesor.name} ${profesor.surname} -> Fecha: ${profesor.date}\n")
        }
    }

    fun asignarProfe(asigName: String, newAsinTeacher: String) {
        val tv_asignaturas = findViewById<TextView>(R.id.tv_asignaturas)
        val til_asigName = findViewById<TextInputLayout>(R.id.til_asigName)
        val til_asigTeacher = findViewById<TextInputLayout>(R.id.til_asigTeacher)

        if (asigName.trim().isNotEmpty()) {
            var asignatura: List<AsignaturaEntity> = database.asignaturaDao.getAsignaturaByName(asigName)

            if (asignatura.size > 0) {
                if(newAsinTeacher == "" || newAsinTeacher == "Sin Profesor"){
                    asignatura.first().teacher = null
                }else {
                    asignatura.first().teacher = newAsinTeacher
                }
                database.asignaturaDao.update(asignatura.first())
                Toast.makeText(this, "Profesor asignado", Toast.LENGTH_SHORT).show()
                showAsignaturas(database, tv_asignaturas)
            }
        }else{
            til_asigName.error = "Rellena el campo"
            til_asigName.requestFocus()
        }
    }

    fun setTeacherDropdown(){
        val et_asigTeacher = findViewById<AutoCompleteTextView>(R.id.et_asigTeacher)
        val profesores = database.profesorDao.getAllProfesores()
        val profesoresList: MutableList<String> = mutableListOf()
        profesoresList.add("Sin Profesor")
        profesores.forEach { profesor ->
            profesoresList.add(profesor.name)
        }

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            profesoresList.toList()
        )

        et_asigTeacher.setAdapter(adapter)

        et_asigTeacher.setOnItemClickListener { parent, _, position, _ ->
            selectedTeacher = profesoresList.toList()[position]
        }
    }
}