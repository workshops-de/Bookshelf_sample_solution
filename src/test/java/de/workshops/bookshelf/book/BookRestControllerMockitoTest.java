package de.workshops.bookshelf.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@WebMvcTest(BookRestController.class)
class BookRestControllerMockitoTest {

    @Autowired
    private BookRestController bookRestController;

    @MockitoBean
    private BookService bookService;

    @Test
    void getAllBooks() {
        Mockito.when(bookService.getAllBooks()).thenReturn(List.of(new Book()));

        assertNotNull(bookRestController.getAllBooks().getBody());
        assertEquals(HttpStatus.OK, bookRestController.getAllBooks().getStatusCode());
        assertEquals(1, bookRestController.getAllBooks().getBody().size());

        Mockito.when(bookService.getAllBooks()).thenReturn(Collections.emptyList());

        assertNull(bookRestController.getAllBooks().getBody());
        assertEquals(HttpStatus.NOT_FOUND, bookRestController.getAllBooks().getStatusCode());
    }
}
