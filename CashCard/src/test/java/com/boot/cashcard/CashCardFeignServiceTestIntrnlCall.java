package com.boot.cashcard;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import com.boot.cashcard.controller.CashCardController;
import com.boot.cashcard.model.CashCard;
import com.boot.cashcard.repo.CashCardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;

/*Note:
Calling your own controller via Feign is a sign of architectural misunderstanding. Use service classes for internal logic.
***************--------end---------********************
will get issues:
================
    ->You can technically use Feign to call localhost, but:
        1) The app must be running (webEnvironment = DEFINED_PORT)
        2) You risk recursion, latency, and threading issues
        3) Security/auth must be handled
***************--------end---------********************
✅ Correct Options for Internal Calls
Goal	Recommended Approach
Access controller logic	Use the controller as a bean (inject it) or call the service layer
Simulate HTTP (for test or separate service)	Use Feign with localhost
Integration tests	Use TestRestTemplate or MockMvc
Production code (internal call)	Use the Service class, not Feign
 */

@WebMvcTest(CashCardController.class)
@AutoConfigureMockMvc
class CashCardFeignServiceTestIntrnlCall {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CashCardRepository repo;

    @Test
    void shouldReturnCashCardById() throws Exception {
        CashCard cashCard = new CashCard(1L, 123.00, "sarah1");
        given(repo.findByIdAndOwner(1L, "sarah1")).willReturn(cashCard);

        mockMvc.perform(get("/cashcards/1")//but it's calling controller
                        .with(user("sarah1").roles("CARD-OWNER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.amount").value(123.00))
                .andExpect(jsonPath("$.owner").value("sarah1"));
    }
}
