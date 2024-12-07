package com.example.libraryapp.domain.usecase

object UseCaseProvider {
    private var getBooksUseCase: GetBooksUseCase? = null
    //private var getBookDetailUseCase: GetBookDetailUseCase? = null
    private var addBookUseCase: AddBookUseCase? = null

    fun provideGetBooksUseCase(): GetBooksUseCase {
        if (getBooksUseCase == null) {
            getBooksUseCase = GetBooksUseCase()
        }
        return getBooksUseCase!!
    }

    // TODO implement the useCases first
  /*  fun provideGetBookDetailUseCase(): GetBookDetailUseCase {
        if (getBookDetailUseCase == null) {
            getBookDetailUseCase = GetBookDetailUseCase()
        }
        return getBookDetailUseCase!!
    } */

    fun provideAddBookUseCase(): AddBookUseCase {
        if (addBookUseCase == null) {
            addBookUseCase = AddBookUseCase()
        }
        return addBookUseCase!!
    }
}