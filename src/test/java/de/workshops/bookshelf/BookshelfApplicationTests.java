package de.workshops.bookshelf;

import de.workshops.bookshelf.config.BookshelfProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
		properties = {
				"spring.datasource.url=jdbc:tc:postgresql:13:///springboot"
		}
)
@Testcontainers
class BookshelfApplicationTests {

	@Autowired
	private BookshelfProperties bookshelfProperties;

	@Test
	void contextLoads() {
		assertEquals(11, bookshelfProperties.getSomeNumber());
		assertEquals("More information", bookshelfProperties.getSomeText());
	}
}
