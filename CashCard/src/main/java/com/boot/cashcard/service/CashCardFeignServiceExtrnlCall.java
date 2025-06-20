package com.boot.cashcard.service;

import com.boot.cashcard.fiegnclient.CashCardClient;
import com.boot.cashcard.model.CashCard;
import org.springframework.stereotype.Service;

@Service
public class CashCardFeignServiceExtrnlCall {
    private final CashCardClient client;

    public CashCardFeignServiceExtrnlCall(CashCardClient client) {
        this.client = client;
    }

    public CashCard getCashCardById(Long id) {
        return client.getCashCard(id).getBody();
    }
}
