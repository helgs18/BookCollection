package no.uia.ikt205.mybooks.books

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import no.uia.ikt205.mybooks.BookHolder
import no.uia.ikt205.mybooks.EXTRA_BOOK_INFO
import no.uia.ikt205.mybooks.books.data.Book
import no.uia.ikt205.mybooks.databinding.ActivityBookDetailsBinding

class BookDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookDetailsBinding
    private lateinit var book:Book

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val receivedBook = intent.getParcelableExtra<Book>(EXTRA_BOOK_INFO)
        //val receivedBook = BookHolder.PickedBook

        if(receivedBook != null){
            book = receivedBook
            Log.i("Details view", receivedBook.toString())
        } else{
            finish()
            return
        }

        binding.title.text = book.title
        binding.author.text = book.author
        binding.year.text = book.published.toString()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        // For å sende ting til bake bruk setResult og intent. Husk å bruke samme tall som du brukte med startActivityForResult i MainActivity.
        finish()
    }
}