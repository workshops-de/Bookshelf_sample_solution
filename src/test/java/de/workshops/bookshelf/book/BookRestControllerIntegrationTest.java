package de.workshops.bookshelf.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class BookRestControllerIntegrationTest {

    @Autowired
    private BookRestController bookRestController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllBooks() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(BookRestController.REQUEST_URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].title", is("Clean Code")))
                .andReturn();
        String jsonPayload = mvcResult.getResponse().getContentAsString();

        Book[] books = objectMapper.readValue(jsonPayload, Book[].class);
        assertEquals(3, books.length);
        assertEquals("Clean Code", books[1].getTitle());
    }

    @Test
    void testWithRestAssuredMockMvc() {
        RestAssuredMockMvc.standaloneSetup(bookRestController);
        RestAssuredMockMvc.
                given().
                log().all().
                when().
                get("/book").
                then().
                log().all().
                statusCode(200).
                body("author[0]", equalTo("Erich Gamma"));
    }

    @Test
    void testWithRestAssured() {
        RestAssured.
                given().
                log().all().
                when().
                get("/book").
                then().
                log().all().
                statusCode(200).
                body("author[0]", equalTo("Erich Gamma"));
    }
}
