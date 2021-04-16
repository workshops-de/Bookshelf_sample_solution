package de.workshops.bookshelf.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(
        properties = {
                "spring.datasource.url=jdbc:tc:postgresql:13:///springboot"
        }
)
@Testcontainers
class BookRestControllerTest {

    @Autowired
    private BookRestController bookRestController;

    @Test
    void getAllBooks() {
        assertNotNull(bookRestController.getAllBooks().getBody());
        assertEquals(3, bookRestController.getAllBooks().getBody().size());
    }
}
