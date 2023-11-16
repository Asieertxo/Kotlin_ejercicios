package com.example.ad_roomasignatura

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "asignatura",
    foreignKeys = [ForeignKey(entity = ProfesorEntity::class, parentColumns = ["name"], childColumns = ["teacher"])]
)
data class AsignaturaEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "teacher")
    var teacher: String?,
)
