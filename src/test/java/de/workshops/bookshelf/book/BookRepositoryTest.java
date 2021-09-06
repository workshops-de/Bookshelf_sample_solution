package de.workshops.bookshelf.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void createBook() {
        String isbn = "123-4567890";
        Book book = buildBook(isbn);
        book = bookRepository.save(book);

        List<Book> books = StreamSupport
                .stream(bookRepository.findAll().spliterator(), false)
                .collect(Collectors.toUnmodifiableList());

        assertNotNull(books);
        assertEquals(4, books.size());
        assertEquals(book.getTitle(), books.get(3).getTitle());
    }

    @Test
    void findBookByIsbn() {
        String isbn = "123-4567890";
        Book book = buildBook(isbn);
        bookRepository.save(book);

        Book newBook = bookRepository.findByIsbn(isbn);

        assertNotNull(newBook);
        assertEquals(book.getTitle(), newBook.getTitle());
    }

    private Book buildBook(String isbn) {
        return Book.builder()
                .title("Title")
                .author("Author")
                .description("Description")
                .isbn(isbn)
                .build();
    }
}
