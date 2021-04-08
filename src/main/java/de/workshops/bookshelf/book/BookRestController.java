package de.workshops.bookshelf.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
