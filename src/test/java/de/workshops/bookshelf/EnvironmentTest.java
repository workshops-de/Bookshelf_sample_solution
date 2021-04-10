package de.workshops.bookshelf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("prod")
class EnvironmentTest {

	@Autowired
	private Environment environment;

	@Value("${server.port}")
	private int port;

	@Test
	void verifyProdPort() {
		assertArrayEquals(new String[]{"prod"}, environment.getActiveProfiles());
		assertEquals(8090, port);
	}
}
