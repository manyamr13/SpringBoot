package com.boot.cashcard.fiegnclient;

import com.boot.cashcard.model.CashCard;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "cashCardClient", url = "${external.api.base-url}")
public interface CashCardClient {
    @GetMapping("/cashcards/{id}")
    ResponseEntity<CashCard> getCashCard(@PathVariable("id") Long id);
}
