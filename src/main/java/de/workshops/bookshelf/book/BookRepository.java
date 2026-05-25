package de.workshops.bookshelf.book;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;

@Repository
@RequiredArgsConstructor
class BookRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<Book> findAllBooks() {
        String sql = "SELECT * FROM book";

    private final JsonMapper mapper;
    private final ResourceLoader resourceLoader;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Book.class));
    }

    public void createBook(Book book) {
        String sql = "INSERT INTO book (title, description, author, isbn) VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(
                sql,
                book.getTitle(),
                book.getDescription(),
                book.getAuthor(),
                book.getIsbn()
        );
    }

    public void deleteBook(Book book) {
        String sql = "DELETE FROM book WHERE isbn = ?";

        jdbcTemplate.update(
                sql,
                book.getIsbn()
        );
    }
}
