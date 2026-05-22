package de.workshops.bookshelf;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookRestController {

    private final JsonMapper mapper;

    private final ResourceLoader resourceLoader;

    private List<Book> books;

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
}
