package com.example.libraryapp.data.datasource

import com.example.libraryapp.domain.model.Book

object BookDatabase {
    private val books = mutableListOf<Book>()

    init {
        // Initial data
        books.addAll(listOf(
            Book(
                id = 1,
                title = "1984",
                author = "George Orwell",
                year = 1949,
                description = "Classic dystopia about surveillance and control...",
                isAvailable = true
            ),
            Book(
                id = 2,
                title = "Fahrenheit 451",
                author = "Ray Bradbury",
                year = 1953,
                description = "In a dystopian future, books are banned...",
                isAvailable = false
            ),
            Book(
                id = 3,
                title = "Brave New World",
                author = "Aldous Huxley",
                year = 1932,
                description = "A futuristic society dominated by technology and genetics...",
                isAvailable = true
            ),
            Book(
                id = 4,
                title = "The Catcher in the Rye",
                author = "J.D. Salinger",
                year = 1951,
                description = "A rebellious teenager reflects on life and his surroundings...",
                isAvailable = true
            ),
            Book(
                id = 5,
                title = "To Kill a Mockingbird",
                author = "Harper Lee",
                year = 1960,
                description = "A lawyer fights for racial justice in the southern United States...",
                isAvailable = false
            ),
            Book(
                id = 6,
                title = "Moby-Dick",
                author = "Herman Melville",
                year = 1851,
                description = "Captains Ahab's obsessive hunt for a white whale...",
                isAvailable = true
            ),
            Book(
                id = 7,
                title = "Pride and Prejudice",
                author = "Jane Austen",
                year = 1813,
                description = "A classic romance that explores prejudice and English society...",
                isAvailable = true
            ),
            Book(
                id = 8,
                title = "The Great Gatsby",
                author = "F. Scott Fitzgerald",
                year = 1925,
                description = "A mysterious millionaire seeks to recover lost love in the Roaring 20's...",
                isAvailable = false
            ),
        ))
    }

    fun getBooks(): List<Book> = books.toList()

    fun getBook(id: Int): Book? = books.find { it.id == id }

    fun addBook(book: Book) {
        books.add(book.copy(id = books.size + 1))
    }

    fun updateBook(book: Book) {
        val index = books.indexOfFirst { it.id == book.id }
        if (index != -1) {
            books[index] = book
        } else {
            throw Exception("Book not found")
        }
    }

    fun deleteBook(id: Int) {
        books.removeIf { it.id == id }
    }

}