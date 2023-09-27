package az.library.bookservice.service;

import az.library.bookservice.dto.BookDto;
import az.library.bookservice.dto.BookIdDto;
import az.library.bookservice.exception.BookNotFoundException;
import az.library.bookservice.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final BookRepository repository;

    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    public List<BookDto> getAllBooks() {
        return repository.findAll()
                .stream()
                .map(BookDto::convert)
                .collect(Collectors.toList());
    }

    public BookIdDto findByIsbn(String isbn) {
        return repository.getBookByIsbn(isbn)
                .map(book -> new BookIdDto(book.getId(), book.getIsbn()))
                .orElseThrow(() -> new BookNotFoundException("Book could not found by isbn: " + isbn));
    }

    public BookDto findBookDetailsById(String id) {
        return repository.findById(id)
                .map(BookDto::convert)
                .orElseThrow(() -> new BookNotFoundException("Book could not found by id:" + id));
    }
}
