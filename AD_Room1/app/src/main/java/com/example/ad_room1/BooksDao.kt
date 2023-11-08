package com.example.ad_room1

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BooksDao {

    @Insert
    fun insert(book: BookEntity)

    @Query("SELECT * FROM books")
    fun getAllBooks(): List<BookEntity>

    @Update
    fun update(book: BookEntity)

    @Delete
    fun delete(book: BookEntity)

    @Query("SELECT * FROM books WHERE author = :author")
    fun getBooksByAuthor(author: String): List<BookEntity>

    @Query("SELECT title FROM books")
    fun getAllTitles(): List<String>

}