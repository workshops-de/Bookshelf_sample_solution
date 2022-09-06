package de.workshops.bookshelf.book;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

<<<<<<< HEAD
    Book findByIsbn(String isbn);
=======
    private final JdbcTemplate jdbcTemplate;

    public List<Book> findAllBooks() {
        String sql = "SELECT * FROM book";

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
>>>>>>> Create_and_test_a_BookRepository_based_on_JDBCTemplate
}
