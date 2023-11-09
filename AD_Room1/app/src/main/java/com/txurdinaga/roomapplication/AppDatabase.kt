package com.txurdinaga.roomapplication

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BookEntity::class, AuthorEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract val booksDao: BooksDao
    abstract val authorsDao: AuthorsDao

    companion object {
        const val DATABASE_NAME = "db-adibide"
    }
}
