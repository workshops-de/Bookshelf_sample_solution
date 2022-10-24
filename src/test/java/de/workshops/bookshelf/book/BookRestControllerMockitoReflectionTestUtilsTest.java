package de.workshops.bookshelf.book;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BookRestControllerMockitoReflectionTestUtilsTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookRestController bookRestController;

    @Test
    void getAllBooks() {
        ReflectionTestUtils.setField(bookService, "books", Collections.emptyList());

        assertNotNull(bookRestController.getAllBooks().getBody());
        assertEquals(0, bookRestController.getAllBooks().getBody().size());
    }
}
