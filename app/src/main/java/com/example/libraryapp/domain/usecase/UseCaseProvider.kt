package com.example.libraryapp.domain.usecase

object UseCaseProvider {
    private var getBooksUseCase: GetBooksUseCase? = null
    private var getBookByIdUseCase: GetBookByIdUseCase? = null
    private var addBookUseCase: AddBookUseCase? = null
    private var updateBookUseCase: UpdateBookUseCase? = null
    private var deleteBookByIdUseCase: DeleteBookByIdUseCase? = null

    fun provideGetBooksUseCase(): GetBooksUseCase {
        if (getBooksUseCase == null) {
            getBooksUseCase = GetBooksUseCase()
        }
        return getBooksUseCase!!
    }

   fun provideGetBookByIdUseCase(): GetBookByIdUseCase {
        if (getBookByIdUseCase == null) {
            getBookByIdUseCase = GetBookByIdUseCase()
        }
        return getBookByIdUseCase!!
    }

    fun provideAddBookUseCase(): AddBookUseCase {
        if (addBookUseCase == null) {
            addBookUseCase = AddBookUseCase()
        }
        return addBookUseCase!!
    }

    fun provideUpdateBookUseCase(): UpdateBookUseCase {
        if (updateBookUseCase == null) {
            updateBookUseCase = UpdateBookUseCase()
        }
        return updateBookUseCase!!
    }

    fun provideDeleteBookByIdUseCase(): DeleteBookByIdUseCase {
        if (deleteBookByIdUseCase == null) {
            deleteBookByIdUseCase = DeleteBookByIdUseCase()
        }
        return deleteBookByIdUseCase!!
    }
}