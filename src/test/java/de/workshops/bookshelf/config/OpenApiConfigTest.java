package de.workshops.bookshelf.config;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.EnabledIf;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
@ActiveProfiles("dev")
class OpenApiConfigTest {

  @Test
  @EnabledIf(
      expression = "#{environment.acceptsProfiles('dev')}",
      loadContext = true
  )
  void verifyProdPort(
      @Autowired BookshelfApplicationProperties bookshelfApplicationProperties
  ) {
    assertTrue(bookshelfApplicationProperties.getCustomOpenApiConfig().isEnabled());
  }
}
