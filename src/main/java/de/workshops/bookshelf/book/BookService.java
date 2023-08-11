package de.workshops.bookshelf.book;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
class BookService {

    private final BookRepository bookRepository;

    List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    Book searchBookByIsbn(String isbn) throws BookException {
        final var book = bookRepository.findByIsbn(isbn);
        if (book == null) {
            throw new BookException();
        }
        return book;
    }

    Book searchBookByAuthor(String author) throws BookException {
        return getAllBooks().stream().filter(book -> hasAuthor(book, author)).findFirst().orElseThrow(BookException::new);
    }

    List<Book> searchBooks(BookSearchRequest request) {
        return getAllBooks().stream()
                .filter(book -> hasAuthor(book, request.author()))
                .filter(book -> hasIsbn(book, request.isbn()))
                .toList();
    }

    Book createBook(Book book) {
        bookRepository.save(book);
        return book;
    }

    private boolean hasIsbn(Book book, String isbn) {
        return book.getIsbn().equals(isbn);
    }

    private boolean hasAuthor(Book book, String author) {
        return book.getAuthor().contains(author);
    }
}
