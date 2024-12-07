package com.example.libraryapp.data.repository

import com.example.libraryapp.domain.repository.BookRepository

object BookRepositoryProvider {
    private var repository: BookRepository? = null

    fun provideRepository(): BookRepository {
        if (repository == null) {
            repository = BookRepositoryImpl()
        }
        return repository!!
    }
}