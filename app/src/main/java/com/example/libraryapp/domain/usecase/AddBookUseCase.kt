package com.example.libraryapp.domain.usecase

import com.example.libraryapp.data.repository.BookRepositoryProvider
import com.example.libraryapp.domain.model.Book

class AddBookUseCase {
    private val repository = BookRepositoryProvider.provideRepository()

    suspend operator fun invoke(
        title: String,
        author: String,
        year: Int,
        description: String,
        ): Book {

        val book = Book(
            id = 0,
            title = title,
            author = author,
            year = year,
            description = description,
            isAvailable = true
        )

        return repository.addBook(book)
    }
}