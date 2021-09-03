package de.workshops.bookshelf.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/book")
public class BookRestController {

    @Autowired
    private ObjectMapper mapper;

    private List<Book> books;

    @PostConstruct
    public void init() throws Exception {
        this.books = Arrays.asList(mapper.readValue(new File("target/classes/books.json"), Book[].class));
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
    public Book searchBookByAuthor(@RequestParam String author) throws Exception {
        return this.books.stream().filter(book -> hasAuthor(book, author)).findFirst().orElseThrow(Exception::new);
    }

    @PostMapping("/search")
    public List<Book> searchBooks(@RequestBody BookSearchRequest request) {
        return this.books.stream()
                .filter(book -> hasAuthor(book, request.getAuthor()))
                .filter(book -> hasIsbn(book, request.getIsbn()))
                .collect(
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                Collections::unmodifiableList
                        )
                );
    }

    private boolean hasIsbn(Book book, String isbn) {
        return book.getIsbn().equals(isbn);
    }

    private boolean hasAuthor(Book book, String author) {
        return book.getAuthor().contains(author);
    }
}
