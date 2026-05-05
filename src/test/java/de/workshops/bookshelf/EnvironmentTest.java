package de.workshops.bookshelf;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.workshops.bookshelf.config.TestcontainersConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.EnabledIf;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Import(TestcontainersConfiguration.class)
class EnvironmentTest {

	@Value("${server.port}")
	private int port;

	@Test
	@EnabledIf(expression = "#{environment['spring.profiles.active'] == 'prod'}", loadContext = true)
	void verifyProdPort() {
		assertEquals(8090, port);
	}
}
