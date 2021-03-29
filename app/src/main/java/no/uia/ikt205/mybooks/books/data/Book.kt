package no.uia.ikt205.mybooks.books.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class BookCollection(val title:String):Parcelable {

    var  books:MutableList<Book> = mutableListOf<Book>()
    get() { return field }
    private set

    constructor(title: String, books:List<Book>) : this(title) {
        this.books = books.toMutableList()
    }

    fun readCount():Int {
        return books.count { it.read } ?: 0
    }

    fun addBook(book:Book){
        if (!books.contains(book)){
            books.add(book)
        }
    }

    fun removeBook(book:Book){
        books.remove(book)
    }

}

@Parcelize
data class Book(val author:String, val title:String, val published:Int, var read:Boolean):Parcelable