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
                description = "Distopía clásica sobre vigilancia y control...",
                isAvailable = true
            ),
            Book(
                id = 2,
                title = "Fahrenheit 451",
                author = "Ray Bradbury",
                year = 1953,
                description = "En un futuro distópico, los libros están prohibidos...",
                isAvailable = false
            ),
            Book(
                id = 3,
                title = "Brave New World",
                author = "Aldous Huxley",
                year = 1932,
                description = "Una sociedad futurista dominada por la tecnología y la genética...",
                isAvailable = true
            ),
            Book(
                id = 4,
                title = "The Catcher in the Rye",
                author = "J.D. Salinger",
                year = 1951,
                description = "Un adolescente rebelde reflexiona sobre la vida y su entorno...",
                isAvailable = true
            ),
            Book(
                id = 5,
                title = "To Kill a Mockingbird",
                author = "Harper Lee",
                year = 1960,
                description = "Un abogado lucha por la justicia racial en el sur de los Estados Unidos...",
                isAvailable = false
            ),
            Book(
                id = 6,
                title = "Moby-Dick",
                author = "Herman Melville",
                year = 1851,
                description = "La obsesiva caza de una ballena blanca por parte del Capitán Ahab...",
                isAvailable = true
            ),
            Book(
                id = 7,
                title = "Pride and Prejudice",
                author = "Jane Austen",
                year = 1813,
                description = "Un romance clásico que explora los prejuicios y la sociedad inglesa...",
                isAvailable = true
            ),
            Book(
                id = 8,
                title = "The Great Gatsby",
                author = "F. Scott Fitzgerald",
                year = 1925,
                description = "Un millonario misterioso busca recuperar el amor perdido en los locos años 20...",
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