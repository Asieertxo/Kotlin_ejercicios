package com.example.ad_roomasignatura

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [AsignaturaEntity::class, ProfesorEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract val asignaturaDao: AsignaturaDao
    abstract val profesorDao: ProfesorDao
    abstract val asignaturaProfesorDao: AsignaturaProfesorDao

    companion object {
        const val DATABASE_NAME = "db-adibide"
    }
}