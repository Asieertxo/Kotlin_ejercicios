package com.example.ad_roomasignatura

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AsignaturaDao {
    @Insert
    fun insert(asignatura: AsignaturaEntity)

    @Query("SELECT * FROM asignatura")
    fun getAllAsignaturas(): List<AsignaturaEntity>

    @Query("SELECT * FROM asignatura WHERE name = :name")
    fun getAsignaturaByName(name: String): List<AsignaturaEntity>

    @Query("SELECT * FROM asignatura WHERE teacher = :teacher")
    fun getAsignaturaByTeacher(teacher: String): List<AsignaturaEntity>

    @Update
    fun update(asignatura: AsignaturaEntity)

    @Delete
    fun delete(asignatura: AsignaturaEntity)
}