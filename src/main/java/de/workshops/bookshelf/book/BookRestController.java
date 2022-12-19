package de.workshops.bookshelf.book;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/book")
@Validated
public class BookRestController {

    private final ObjectMapper mapper;
    private final ResourceLoader resourceLoader;

    private List<Book> books;

    public BookRestController(ObjectMapper mapper, ResourceLoader resourceLoader) {
        this.mapper = mapper;
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    public void init() throws IOException {
        final var resource = resourceLoader.getResource("classpath:books.json");
        books = mapper.readValue(resource.getInputStream(), new TypeReference<>() {});
    }
    
    @GetMapping
    public List<Book> getAllBooks() {
        return books;
    }

    @GetMapping("/{isbn}")
    public Book getSingleBook(@PathVariable String isbn) throws BookException {
        return this.books.stream().filter(book -> hasIsbn(book, isbn)).findFirst().orElseThrow(BookException::new);
    }

    @GetMapping(params = "author")
    public Book searchBookByAuthor(@RequestParam @NotBlank @Size(min = 3) String author) throws BookException {
        return this.books.stream().filter(book -> hasAuthor(book, author)).findFirst().orElseThrow(BookException::new);
    }

    @PostMapping("/search")
    public List<Book> searchBooks(@RequestBody @Valid BookSearchRequest request) {
        return this.books.stream()
                .filter(book -> hasAuthor(book, request.author()))
                .filter(book -> hasIsbn(book, request.isbn()))
                .toList();
    }

    @ExceptionHandler(BookException.class)
    public ResponseEntity<String> error(BookException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.I_AM_A_TEAPOT);
    }

    private boolean hasIsbn(Book book, String isbn) {
        return book.getIsbn().equals(isbn);
    }

    private boolean hasAuthor(Book book, String author) {
        return book.getAuthor().contains(author);
    }
}
