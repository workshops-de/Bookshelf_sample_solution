package de.workshops.bookshelf.book;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping(BookRestController.REQUEST_URL)
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:3000" })
public class BookRestController {

    static final String REQUEST_URL = "/book";

    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
<<<<<<< HEAD
        List<Book> result = bookService.getBooks();
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
=======
        List<Book> result = bookService.getAllBooks();
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
>>>>>>> Enable_Spring_Boot_Actuator
        } else {
            return ResponseEntity.ok(result);
        }
    }

    @GetMapping("/{isbn}")
    public Book getBookByIsbn(@PathVariable String isbn) throws BookException {
        return bookService.searchBookByIsbn(isbn);
    }

    @GetMapping(params = "author")
    public Book getBookByAuthor(@RequestParam @NotBlank @Size(min = 3) String author) throws BookException {
        return bookService.searchBookByAuthor(author);
    }

    @PostMapping("/search")
    public List<Book> searchBooks(@RequestBody @Valid BookSearchRequest request) {
        return bookService.searchBooks(request);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<String> deleteBook(@PathVariable String isbn) throws BookException {
        bookService.deleteBook(bookService.getSingleBook(isbn));

        return ResponseEntity.ok("OK");
    }

    @ExceptionHandler(BookException.class)
    public ProblemDetail error(BookException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Book Not Found");
        problemDetail.setType(URI.create("http://localhost:8080/book_exception.html"));
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }
}
