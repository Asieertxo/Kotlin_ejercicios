package com.txurdinaga.roomapplication

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "books",
    foreignKeys = [
        ForeignKey(entity = AuthorEntity::class, parentColumns = ["name"], childColumns = ["author"])
    ]
)

data class BookEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0,

    @ColumnInfo(name = "title")
    @NonNull
    var title: String,

    @ColumnInfo(name = "author")
    @NonNull
    var author: String,

    @ColumnInfo(name = "pub_date")
    @NonNull
    var pubDate: String

) {


}
