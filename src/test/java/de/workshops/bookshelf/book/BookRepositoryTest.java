package de.workshops.bookshelf.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("db-test")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void createBook() {
        String isbn = "123-4567890";
        Book book = buildAndSaveBook(isbn);

        List<Book> books = StreamSupport
                .stream(bookRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        assertNotNull(books);
        assertEquals(1, books.size());
        assertEquals(book.getTitle(), books.get(0).getTitle());
    }

    @Test
    void findBookByIsbn() {
        String isbn = "123-4567890";
        Book book = buildAndSaveBook(isbn);

        Book newBook = bookRepository.findByIsbn(isbn);

        assertNotNull(newBook);
        assertEquals(book.getTitle(), newBook.getTitle());
    }

    private Book buildAndSaveBook(String isbn) {
        Book book = Book.builder()
                .title("Title")
                .author("Author")
                .description("Description")
                .isbn(isbn)
                .build();
        bookRepository.save(book);
        return book;
    }
}
