package az.library.libraryservice.dto

import az.library.libraryservice.dto.BookIdDto


data class BookDto @JvmOverloads constructor(val id: BookIdDto? = null,
                                             val title: String? = "",
                                             val year: Int? = 0,
                                             val author: String? = "",
                                             val pressName: String? = "") {



}