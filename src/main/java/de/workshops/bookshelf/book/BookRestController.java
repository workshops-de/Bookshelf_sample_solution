package de.workshops.bookshelf.book;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.IOException;
import java.util.List;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

@RestController
@RequestMapping("/book")
@Validated
public class BookRestController {

    private final JsonMapper mapper;

    private final ResourceLoader resourceLoader;

    private List<Book> books;

    public BookRestController(JsonMapper mapper, ResourceLoader resourceLoader) {
        this.mapper = mapper;
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    public void init() throws IOException {
        this.books = mapper.readValue(
                resourceLoader
                        .getResource("classpath:books.json")
                        .getInputStream(),
                new TypeReference<>() {}
        );
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
