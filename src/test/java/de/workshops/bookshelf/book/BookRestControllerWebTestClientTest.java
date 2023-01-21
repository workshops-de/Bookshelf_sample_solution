package de.workshops.bookshelf.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookRestControllerWebTestClientTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldReturnAllBooks() {
        webTestClient.get()
                .uri("/book")
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody()
                .jsonPath("$").isArray()
                .jsonPath("$.length()").isEqualTo(3)
                .jsonPath("$[1].title").isEqualTo("Clean Code");
    }

    @Test
    void shouldReturnBookByIsbn() {
        webTestClient.get()
                .uri("/book/{isbn}", "978-3836211161")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(Book.class)
                .consumeWith(entityExchangeResult -> {
                    final var responseBody = entityExchangeResult.getResponseBody();
                    assertThat(responseBody).isNotNull()
                            .hasFieldOrPropertyWithValue("author", "Gottfried Wolmeringer")
                            .hasFieldOrPropertyWithValue("title", "Coding for Fun");
                });
    }

    @Test
    void shouldReturnBookByAuthor() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/book")
                        .queryParam("author", "Erich Gamma")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .consumeWith(entityExchangeResult -> {
                    final var responseBody = entityExchangeResult.getResponseBody();
                    assertThat(responseBody).isNotNull()
                            .hasFieldOrPropertyWithValue("author", "Erich Gamma")
                            .hasFieldOrPropertyWithValue("title", "Design Patterns");
                });
    }

    @Test
    void shouldSearchBook() {
        final var bookSearchRequest = new BookSearchRequest("Erich Gamma", "978-0201633610");
        webTestClient.post()
                .uri("/book/search")
                .body(Mono.just(bookSearchRequest), BookSearchRequest.class)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(Book.class)
                .consumeWith(listEntityExchangeResult -> {
                    final var foundBooks = listEntityExchangeResult.getResponseBody();
                    assertThat(foundBooks).hasSize(1)
                            .first()
                            .hasFieldOrPropertyWithValue("author", "Erich Gamma")
                            .hasFieldOrPropertyWithValue("title", "Design Patterns");
                });
    }
}
