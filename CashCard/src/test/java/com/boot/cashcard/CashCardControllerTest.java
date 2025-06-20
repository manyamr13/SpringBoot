package com.boot.cashcard;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import java.io.IOException;
import java.net.URI;
import java.util.Base64;
import java.util.List;

import org.springframework.http.MediaType;
import org.json.JSONArray;
import static org.assertj.core.api.Assertions.assertThat;



@SpringBootTest
@AutoConfigureMockMvc
@EnableFeignClients(basePackages = "com.boot.cashcard.fiegnclient")
public class CashCardControllerTest {

    private static MockWebServer mockWebServer;

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    public static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @DynamicPropertySource
    public static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("external.api.base-url", () -> mockWebServer.url("/").toString());
    }

    @Test
    void shouldReturnACashCardWhenDataIsSaved() throws Exception {
        // Mocked response for the /cashcards/1 API
        String mockResponse = """
                {
                  "id": 1,
                  "amount": 123
                }
                """;

        mockWebServer.enqueue(new MockResponse()
                .setBody(mockResponse)
                .addHeader("Content-Type", "application/json"));

        MvcResult result = mockMvc.perform(get("/cashcards/1")
                        .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("sarah1:abc123".getBytes())))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        DocumentContext documentContext = JsonPath.parse(responseBody);
        Number id = documentContext.read("$.id");
        assertThat(id).isEqualTo(1);

        Double amount = documentContext.read("$.amount");
        assertThat(amount).isEqualTo(123.0);
    }

    @Test
    void shouldNotReturnACashCardWithAnUnknownId() throws Exception {
        // Simulate a 404 Not Found response from the server
        mockWebServer.enqueue(new MockResponse()
                .setResponseCode(404));

        MvcResult result = mockMvc.perform(get("/cashcards/1000")
                        .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("sarah1:abc123".getBytes())))
                .andExpect(status().isNotFound())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(responseBody).isBlank();
    }

    @Test
    @DirtiesContext // Ensures a clean application context to avoid interference from other tests
    void shouldCreateANewCashCard() throws Exception {
        // Arrange: JSON representation of the new CashCard
        String newCashCardJson = """
        {
            "id": 12,
            "amount": 555.00
        }
        """;

        // Act: Perform POST request to create the new CashCard
        MvcResult postResult = mockMvc.perform(post("/cashcards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newCashCardJson)
                        .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("sarah1:abc123".getBytes())))
                .andExpect(status().isCreated())
                .andReturn();

        String location = postResult.getResponse().getHeader("Location");
        assertThat(location).isNotBlank();

        // Act: Perform GET request to retrieve the newly created CashCard
        MvcResult getResult = mockMvc.perform(get(location)
                        .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("sarah1:abc123".getBytes())))
                .andExpect(status().isOk())
                .andReturn();

        // Assert: Validate the response body
        String responseBody = getResult.getResponse().getContentAsString();
        DocumentContext documentContext = JsonPath.parse(responseBody);
        Number id = documentContext.read("$.id");
        Double amount = documentContext.read("$.amount");

        assertThat(id).isEqualTo(12);
        assertThat(amount).isEqualTo(555.00);
    }

    @Test
    void shouldReturnAllCashCardsWhenListIsRequested() throws Exception {
        MvcResult result = mockMvc.perform(get("/cashcards")
                        .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("sarah1:abc123".getBytes())))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        DocumentContext documentContext = JsonPath.parse(responseBody);

        int cashCardCount = documentContext.read("$.length()");
        assertThat(cashCardCount).isEqualTo(11);

        List<Integer> ids = documentContext.read("$..id");
        assertThat(ids).containsExactlyInAnyOrder(1, 2, 3, 4, 5, 6, 9, 10, 11, 12, 13);


        List<Double> amounts = documentContext.read("$..amount");
        assertThat(amounts).containsExactlyInAnyOrder(555.00, 555.00, 450.00, 450.00, 450.00, 450.00, 1.00, 123.00, 150.00, 357.00, 357.00);
    }

    @Test
    void shouldReturnAPageOfCashCards() throws Exception {
        MvcResult result = mockMvc.perform(get("/cashcards?page=0&size=3")
                        .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("sarah1:abc123".getBytes())))
                .andExpect(status().isOk())
                .andReturn();

        DocumentContext documentContext = JsonPath.parse(result.getResponse().getContentAsString());
        List<?> page = documentContext.read("$[*]");
        assertThat(page.size()).isEqualTo(3);
    }

    @Test
    void shouldReturnASortedPageOfCashCards() throws Exception {
        MvcResult result = mockMvc.perform(get("/cashcards?page=0&size=1&sort=amount,desc")
                        .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("sarah1:abc123".getBytes())))
                .andExpect(status().isOk())
                .andReturn();

        DocumentContext documentContext = JsonPath.parse(result.getResponse().getContentAsString());
        List<?> page = documentContext.read("$[*]");
        assertThat(page.size()).isEqualTo(1);


        double amount = documentContext.read("$[0].amount");
        assertThat(amount).isEqualTo(555.00);
    }

    @Test
    void shouldReturnASortedPageOfCashCardsWithNoParametersAndUseDefaultValues() throws Exception {
        MvcResult result = mockMvc.perform(get("/cashcards")
                        .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("sarah1:abc123".getBytes())))
                .andExpect(status().isOk())
                .andReturn();

        DocumentContext documentContext = JsonPath.parse(result.getResponse().getContentAsString());
        List<?> page = documentContext.read("$[*]");
        assertThat(page.size()).isEqualTo(11);

        List<Double> amounts = documentContext.read("$..amount");
        assertThat(amounts).containsExactly(1.00, 123.00, 150.00, 357.00, 357.00, 450.00, 450.00, 450.00, 450.00, 555.00, 555.00);

    }

    @Test
    void shouldNotReturnACashCardWhenUsingBadCredentials() throws Exception {
        mockMvc.perform(get("/cashcards/1")
                        .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("BAD-USER:abc123".getBytes())))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/cashcards/1")
                        .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("sarah1:BAD-PASSWORD".getBytes())))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldRejectUsersWhoAreNotCardOwners() throws Exception {
        mockMvc.perform(get("/cashcards/1")
                        .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("hank-owns-no-cards:qrs456".getBytes())))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldNotAllowAccessToCashCardsTheyDoNotOwn() throws Exception {
        mockMvc.perform(get("/cashcards/7")
                        .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("sarah1:abc123".getBytes())))
                .andExpect(status().isNotFound());
    }


}





