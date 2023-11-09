package com.txurdinaga.roomapplication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AuthorsDao {
    @Insert
    fun insertMany(authors: ArrayList<AuthorEntity>)

    @Query("SELECT * FROM authors WHERE number_of_books > :numberOfBooks")
    fun getAuthorsWithMoreThanXAmountOfBooks(numberOfBooks: Int): List<AuthorEntity>
}
