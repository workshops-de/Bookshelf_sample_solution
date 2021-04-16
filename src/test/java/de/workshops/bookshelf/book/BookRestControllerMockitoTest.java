package de.workshops.bookshelf.book;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(
        properties = {
                "spring.datasource.url=jdbc:tc:postgresql:13:///springboot"
        }
)
@Testcontainers
class BookRestControllerMockitoTest {

    @Autowired
    private BookRestController bookRestController;

    @MockBean
    private BookService bookService;

    @Test
    void getAllBooks() {
        Mockito.when(bookService.getBooks()).thenReturn(Collections.emptyList());

        assertNotNull(bookRestController.getAllBooks().getBody());
        assertEquals(0, bookRestController.getAllBooks().getBody().size());
    }
}
