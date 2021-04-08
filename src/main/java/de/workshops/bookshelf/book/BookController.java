package de.workshops.bookshelf.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/")
public class BookController {

    @Autowired
    private ObjectMapper mapper;

    private List<Book> books;

    @PostConstruct
    public void init() throws IOException {
        this.books = Arrays.asList(mapper.readValue(new File("target/classes/books.json"), Book[].class));
    }
    
    @GetMapping
    public String getAllBooks(Model model) {
        model.addAttribute("books", books);

        return "books";
    }
}
