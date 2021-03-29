package no.uia.ikt205.mybooks

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import no.uia.ikt205.mybooks.books.data.Book
import no.uia.ikt205.mybooks.books.BookCollectionAdapter
import no.uia.ikt205.mybooks.books.BookDepositoryManager
import no.uia.ikt205.mybooks.books.BookDetailsActivity
import no.uia.ikt205.mybooks.databinding.ActivityMainBinding


const val EXTRA_BOOK_INFO: String = "no.uia.ikt205.mybooks.book.info"


class BookHolder{
    companion object{
        var PickedBook:Book? = null
    }
}


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.bookListing.layoutManager = LinearLayoutManager(this)
        binding.bookListing.adapter = BookCollectionAdapter(emptyList<Book>(), this::onBookClicked)

        BookDepositoryManager.instance.onBooks = {
            (binding.bookListing.adapter as BookCollectionAdapter).updateCollection(it)
        }

        BookDepositoryManager.instance.load()


        binding.saveBt.setOnClickListener {

            val author = binding.author.text.toString()
            val title = binding.title.text.toString()
            val published = binding.published.text.toString().toInt()

            binding.author.setText("")
            binding.title.setText("")
            binding.published.setText("")

            addBook(title, author, published)

            val ipm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            ipm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

        }
    }

    private fun addBook(title: String, author: String, published: Int) {
        val book = Book(title, author, published, false)
        BookDepositoryManager.instance.addBook(book)
    }


    private fun onBookClicked(book: Book): Unit {

        val intent = Intent(this, BookDetailsActivity::class.java).apply {
            putExtra(EXTRA_BOOK_INFO, book)
        }

        startActivity(intent)

        //startActivityForResult(intent,25) // 25 er bare et random tall som du bestemmer deg for, det er for å gjenkjenne dette intentet senere.
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }


}