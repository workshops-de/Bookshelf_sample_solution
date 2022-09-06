package de.workshops.bookshelf.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void createBook() {
<<<<<<< HEAD
        String isbn = "123-4567890";
        Book book = buildAndSaveBook(isbn);
=======
        Book book = Book.builder()
                .title("Title")
                .author("Author")
                .description("Description")
                .isbn("123-4567890")
                .build();
        bookRepository.createBook(book);
>>>>>>> Create_and_test_a_BookRepository_based_on_JDBCTemplate

        List<Book> books = StreamSupport
                .stream(bookRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        assertNotNull(books);
        assertEquals(4, books.size());
<<<<<<< HEAD
        assertEquals(book.getTitle(), books.get(3).getTitle());
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
=======
        assertEquals(book.getIsbn(), books.get(3).getIsbn());
>>>>>>> Create_and_test_a_BookRepository_based_on_JDBCTemplate
    }
}
