package com.txurdinaga.roomapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.room.Room

class MainActivity : AppCompatActivity() {

    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Construir la base de datos
        database = Room.databaseBuilder(
            application, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .allowMainThreadQueries()
            .build()

        val  btnBooks = findViewById<Button> (R.id.btnBooks)
        val  tvBooks = findViewById<TextView> (R.id.tvBooks)

        btnBooks.setOnClickListener {
            tvBooks.text = ""
            val books = database.booksDao.getAllBooks()
            books.forEach { book ->
                tvBooks.append("${book.id}, ${book.title}, ${book.author}, ${book.pubDate}\n")
            }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val books = database.booksDao.getAllBooks()
        val  tvBooks = findViewById<TextView> (R.id.tvBooks)
        when (item.itemId) {
            R.id.action_insert_all -> saveBooks()
            R.id.action_update -> updateFirstBook(books, tvBooks)
            R.id.action_delete -> deleteFirstBook(books)
            R.id.action_delete_all -> deleteAll()
            R.id.action_books_by_author -> getBooksByAuthor(books, tvBooks)
            R.id.action_all_titles -> getAllTitles(tvBooks)
            R.id.action_insert_authors -> saveAuthors()
            R.id.action_get_authors_and_books -> showAuthorsAndTheirBooks(tvBooks)
        }
        return super.onOptionsItemSelected(item)
    }


    private fun saveBooks() {
        val book1 = BookEntity(title = "Mobby Dick", author = "Herman Melville", pubDate = "1851")
        val book2 = BookEntity(title = "Mobby Dick 2", author = "Herman Melville", pubDate = "1851")
        val book3 = BookEntity(title = "Mobby Dick 3", author = "Herman Melville", pubDate = "1851")
        val book4 = BookEntity(title = "Mobby Dick 4", author = "Herman Melville", pubDate = "1851")
        val book5 = BookEntity(title = "Mobby Dick 5", author = "Herman Melville", pubDate = "1851")
        database.booksDao.insert(book1)
        database.booksDao.insert(book2)
        database.booksDao.insert(book3)
        database.booksDao.insert(book4)
        database.booksDao.insert(book5)
    }

    private fun updateFirstBook(books : List<BookEntity>, tvBooks : TextView){
        if (books.size > 0) {
            books.first().title = "Otro libro"
            database.booksDao.update(books.first())
        }
    }

    private fun deleteFirstBook(books : List<BookEntity>){
        if (books.size > 0) {
            database.booksDao.delete(books.first())
        }
    }

    private fun deleteAll(){
        database.clearAllTables()
    }

    private fun getBooksByAuthor(books : List<BookEntity>, tvBooks : TextView) {
        val books = database.booksDao.getBooksByAuthor("Herman Melville")
        tvBooks.text = ""
        books.forEach { book ->
            tvBooks.append("${book.id}, ${book.title}, ${book.author}, ${book.pubDate}\n")
        }
    }

    private fun getAllTitles(tvBooks : TextView) {
        val titles = database.booksDao.getAllTitles()
        tvBooks.text = ""
        titles.forEach { title ->
            tvBooks.append("$title\n")
        }
    }

    private fun saveAuthors() {
        val authors = ArrayList<AuthorEntity>().apply {
            add(AuthorEntity("Herman Melville", 1819, 2))
            add(AuthorEntity("Miguel de Cervantes", 1547, 1))
            add(AuthorEntity("F. Scott Fitzgerald", 1896, 1))
            add(AuthorEntity("Leo Tolstoy", 1828, 1))
        }
        database.authorsDao.insertMany(authors)
    }

    private fun showAuthorsAndTheirBooks(tvBooks : TextView) {
        val authors = database.authorsDao.getAuthorsWithMoreThanXAmountOfBooks(1)
        tvBooks.text = ""
        authors.forEach { author ->
            tvBooks.append("${author.name}, ${author.yearOfBirth}, ${author.numberOfBooks}\n")
        }
        if (authors.isEmpty()) return
        val booksByAuthor = database.booksDao.getBooksByAuthor(authors[0].name)
        booksByAuthor.forEach { book ->
            tvBooks.append("${book.id}, ${book.title}, ${book.author}, ${book.pubDate}\n")
        }
    }


}
