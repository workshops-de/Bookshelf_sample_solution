package de.workshops.bookshelf.book;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(
        properties = {
                "spring.datasource.url=jdbc:tc:postgresql:13:///springboot"
        }
)
@ExtendWith(MockitoExtension.class)
@Testcontainers
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
