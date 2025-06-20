package com.boot.cashcard;

import com.boot.cashcard.model.CashCard;
import com.boot.cashcard.fiegnclient.CashCardClient;
import com.boot.cashcard.service.CashCardFeignServiceExtrnlCall;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
/* this mechanism is used to call spring boot external service which is running on different server.
Here we are calling some external mocked feign-client service with mock-data and verifying it.
*
* */
@SpringBootTest
@EnableFeignClients(clients = CashCardClient.class)
@Import(CashCardFeignServiceExtrnlCall.class)
public class CashCardFeignClientTestExtrnlCall {

    @Autowired
    private CashCardFeignServiceExtrnlCall service;

    private static MockWebServer mockWebServer;

    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        System.setProperty("external.api.base-url", mockWebServer.url("/").toString());
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    void shouldFetchCashCardById() {
        String responseBody = """
            {
                "id": 1,
                "amount": 123.00,
                "owner": "sarah"
            }
            """;

        mockWebServer.enqueue(new MockResponse()
                .setBody(responseBody)
                .addHeader("Content-Type", "application/json"));

        CashCard card = service.getCashCardById(1L);

        assertThat(card).isNotNull();
        assertThat(card.getId()).isEqualTo(1L);
        assertThat(card.getAmount()).isEqualTo(123.00);
        assertThat(card.getOwner()).isEqualTo("sarah1");
    }
}
