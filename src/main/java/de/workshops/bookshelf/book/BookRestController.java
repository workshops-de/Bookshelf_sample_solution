package de.workshops.bookshelf.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Arrays;
import java.util.List;

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
    public Book getSingleBook(@PathVariable String isbn) {
        return this.books.stream().filter(book -> hasIsbn(book, isbn)).findFirst().orElseThrow();
    }

    @GetMapping(params = "author")
    public Book searchBookByAuthor(@RequestParam String author) {
        return this.books.stream().filter(book -> hasAuthor(book, author)).findFirst().orElseThrow();
    }

    private boolean hasIsbn(Book book, String isbn) {
        return book.getIsbn().equals(isbn);
    }

    private boolean hasAuthor(Book book, String author) {
        return book.getAuthor().contains(author);
    }
}
