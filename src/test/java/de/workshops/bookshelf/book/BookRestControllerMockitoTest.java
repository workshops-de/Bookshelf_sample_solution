package de.workshops.bookshelf.book;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(BookRestController.class)
class BookRestControllerMockitoTest {

    @Autowired
    private BookRestController bookRestController;

    @MockBean
    private BookService bookService;

    @Test
    void getAllBooks() {
<<<<<<< HEAD
        Mockito.when(bookService.getBooks()).thenReturn(Collections.singletonList(new Book()));
=======
        Mockito.when(bookService.getAllBooks()).thenReturn(Collections.emptyList());
>>>>>>> Enable_Spring_Boot_Actuator

        assertNotNull(bookRestController.getAllBooks().getBody());
        assertEquals(HttpStatus.OK, bookRestController.getAllBooks().getStatusCode());
        assertEquals(1, bookRestController.getAllBooks().getBody().size());

        Mockito.when(bookService.getBooks()).thenReturn(Collections.emptyList());

        assertNull(bookRestController.getAllBooks().getBody());
        assertEquals(HttpStatus.NOT_FOUND, bookRestController.getAllBooks().getStatusCode());
    }
}
