package com.example.ad_room1

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BookEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase(){
    abstract val booksDao: BooksDao

    companion object{
        const val DATABASE_NAME = "db-books"
    }
}