package az.library.libraryservice.service;

import az.library.libraryservice.client.BookServiceClient;
import az.library.libraryservice.dto.AddBookRequest;
import az.library.libraryservice.dto.BookDto;
import az.library.libraryservice.dto.BookIdDto;
import az.library.libraryservice.dto.LibraryDto;
import az.library.libraryservice.exception.LibraryNotFoundException;
import az.library.libraryservice.model.Library;
import az.library.libraryservice.repository.LibraryRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LibraryService {

    private final LibraryRepository libraryRepository;
    private final BookServiceClient bookServiceClient;


    public LibraryService(LibraryRepository libraryRepository,
                          BookServiceClient bookServiceClient) {
        this.libraryRepository = libraryRepository;
        this.bookServiceClient = bookServiceClient;
    }

    public LibraryDto getAllBooksInLibraryById(String id) {
        Library library = libraryRepository.findById(id)
                .orElseThrow(() -> new LibraryNotFoundException("Library could not found by id: " + id));

        LibraryDto libraryDto = new LibraryDto(library.getId(),
                library.getUserBook()
                        .stream()
                        .map(book -> bookServiceClient.getBookById(book).getBody())
                        .collect(Collectors.toList()));
        return libraryDto;
    }

    public LibraryDto createLibrary() {
        Library newLibrary = libraryRepository.save(new Library());
        return new LibraryDto(newLibrary.getId());
    }

    public void addBookToLibrary(AddBookRequest request) {

        String bookId = bookServiceClient.getBookByIsbn(request.getIsbn()).getBody().getBookId();

        Library library =libraryRepository.findById(request.getId())
                .orElseThrow(()-> new LibraryNotFoundException(
                        "Library not found by id: " + request.getId()));

        library.getUserBook()
                .add(bookId);
        libraryRepository.save(library);

    }


    public List<String> getAllLibraries() {

        return libraryRepository.findAll()
                .stream()
                .map(l -> l.getId())
                .collect(Collectors.toList());
    }
}
