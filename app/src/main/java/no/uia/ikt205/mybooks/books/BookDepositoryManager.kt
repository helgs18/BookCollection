package no.uia.ikt205.mybooks.books

import android.content.Context
import android.content.res.Resources
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import no.uia.ikt205.mybooks.App
import no.uia.ikt205.mybooks.R
import no.uia.ikt205.mybooks.books.data.Book
import no.uia.ikt205.mybooks.books.data.BookCollection


class BookDepositoryManager {

    private lateinit var bookCollection: BookCollection
    var onBooks: ((List<Book>) -> Unit)? = null
    var onBookUpdate: ((book: Book) -> Unit)? = null

    val books:BookCollection
        get() = this.bookCollection


    fun load() {

        val context = App.context

        if(context != null) {

            val url = context.getString(R.string.book_listing_url)
            val queue = Volley.newRequestQueue(context)

            val request = StringRequest(Request.Method.GET,url, {
                val gson = Gson()
                val typeDef = object : TypeToken<List<Book>>() {}.type
                val booksFromWeb = gson.fromJson<List<Book>>(it.toString(), typeDef)
                bookCollection = BookCollection("My books",booksFromWeb)

                onBooks?.invoke(bookCollection.books)

            }, {
                Log.e("BookDepositoryManager", it.toString())
                onBooks?.invoke(bookCollection.books)
            })

            queue.add(request)
        }
    }

    fun updateBook(book: Book) {
        // finn bok i listen og erstat med den nye boken.
        onBookUpdate?.invoke(book)
    }

    fun addBook(book: Book) {
        bookCollection.addBook(book)
        onBooks?.invoke(bookCollection.books)
    }

    companion object {
        val instance = BookDepositoryManager()
    }

}