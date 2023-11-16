package com.example.ad_roomasignatura

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProfesorDao {
    @Insert
    fun insert(profesor: ProfesorEntity)

    @Query("SELECT * FROM profesor")
    fun getAllProfesores(): List<ProfesorEntity>

    @Query("SELECT * FROM profesor WHERE name = :name")
    fun getProfesorByName(name: String): List<ProfesorEntity>

    @Delete
    fun delete(profesor: ProfesorEntity)
}