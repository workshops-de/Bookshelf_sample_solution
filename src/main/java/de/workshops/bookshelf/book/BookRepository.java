package de.workshops.bookshelf.book;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

@Repository
@RequiredArgsConstructor
class BookRepository {

    private final JsonMapper mapper;
    private final ResourceLoader resourceLoader;

    private List<Book> books;

    @PostConstruct
    void initBookList() throws IOException {
        final var resource = resourceLoader.getResource("classpath:books.json");
        books = mapper.readValue(resource.getInputStream(), new TypeReference<>() {});
    }

    List<Book> findAllBooks() {
        return books;
    }
}
