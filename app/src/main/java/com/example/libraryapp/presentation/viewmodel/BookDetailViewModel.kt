package com.example.libraryapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryapp.domain.model.Book
import com.example.libraryapp.domain.usecase.UseCaseProvider
import kotlinx.coroutines.launch

class BookDetailViewModel : ViewModel() {

    private val getBookByIdUseCase = UseCaseProvider.provideGetBookByIdUseCase()
    private val updateBookUseCase = UseCaseProvider.provideUpdateBookUseCase()
    private val deleteBookByIdUseCase = UseCaseProvider.provideDeleteBookByIdUseCase()

    private val _book = MutableLiveData<Book?>()
    val book: LiveData<Book?> = _book

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _bookDeleted = MutableLiveData<Boolean>()
    val bookDeleted: LiveData<Boolean> get() = _bookDeleted

    fun loadBook(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _book.value = getBookByIdUseCase(id)
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateBook(book: Book) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                updateBookUseCase(book)
                loadBook(id = book.id)
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteBook(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val deletedBook = deleteBookByIdUseCase(id)
                if (deletedBook != null) {
                    _bookDeleted.value = true
                } else {
                    _error.value = "Failed to delete book"
                }
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}
