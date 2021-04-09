package de.workshops.bookshelf.book;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(BookRestController.REQUEST_URL)
@RequiredArgsConstructor
public class BookRestController {

    static final String REQUEST_URL = "/book";

    private final BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getBooks();
    }

    @GetMapping("/{isbn}")
    public Book getSingleBook(@PathVariable String isbn) throws BookException {
        return bookService.getSingleBook(isbn);
    }

    @GetMapping(params = "author")
    public Book searchBookByAuthor(@RequestParam String author) throws BookException {
        return bookService.searchBookByAuthor(author);
    }

    @PostMapping("/search")
    public List<Book> searchBooks(@RequestBody BookSearchRequest request) {
        return bookService.searchBooks(request);
    }

    @ExceptionHandler(BookException.class)
    public ResponseEntity<String> error(BookException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
