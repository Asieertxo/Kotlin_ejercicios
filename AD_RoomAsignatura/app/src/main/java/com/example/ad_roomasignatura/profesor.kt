package com.example.ad_roomasignatura

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "profesor")
data class ProfesorEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "surname")
    @NonNull
    var surname: String,

    @ColumnInfo(name = "date")
    @NonNull
    var date: String,
)