package com.example.ad_roomasignatura

import androidx.room.Dao
import androidx.room.Query

@Dao
interface AsignaturaProfesorDao {

    @Query("SELECT asignatura.name as asignatura_name, profesor.name as profesor_name, profesor.surname AS profesor_surname FROM asignatura LEFT JOIN profesor ON asignatura.teacher = profesor.name")
    fun getAllAsignaturasProfesor(): List<AsignaturaProfesor>
}