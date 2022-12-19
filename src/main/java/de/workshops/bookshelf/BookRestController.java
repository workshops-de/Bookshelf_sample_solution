package de.workshops.bookshelf;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.core.io.ResourceLoader;
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
    public Book getSingleBook(@PathVariable String isbn) throws Exception {
        return this.books.stream().filter(book -> hasIsbn(book, isbn)).findFirst().orElseThrow(Exception::new);
    }

    @GetMapping(params = "author")
    public Book searchBookByAuthor(@RequestParam @NotBlank @Size(min = 3) String author) throws Exception {
        return this.books.stream().filter(book -> hasAuthor(book, author)).findFirst().orElseThrow(Exception::new);
    }

    private boolean hasIsbn(Book book, String isbn) {
        return book.getIsbn().equals(isbn);
    }

    private boolean hasAuthor(Book book, String author) {
        return book.getAuthor().contains(author);
    }
}
