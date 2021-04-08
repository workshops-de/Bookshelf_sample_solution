package de.workshops.bookshelf.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    private final ObjectMapper mapper;

    public List<Book> getBooks() throws IOException {
        return Arrays.asList(mapper.readValue(new File("target/classes/books.json"), Book[].class));
    }
}
