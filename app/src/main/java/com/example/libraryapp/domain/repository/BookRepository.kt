package com.example.libraryapp.domain.repository

import com.example.libraryapp.domain.model.Book

interface BookRepository {

    suspend fun getBooks(): List<Book>
    suspend fun getBookById(id: Int): Book?

    suspend fun addBook(book: Book): Book
    suspend fun updateBook(book: Book): Book

    suspend fun deleteBookById(id: Int): Book?
}