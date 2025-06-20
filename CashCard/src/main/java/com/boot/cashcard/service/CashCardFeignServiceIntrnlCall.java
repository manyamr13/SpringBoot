package com.boot.cashcard.service;

import com.boot.cashcard.model.CashCard;
import com.boot.cashcard.repo.CashCardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class CashCardFeignServiceIntrnlCall {
    private final CashCardRepository repo;

    public CashCardFeignServiceIntrnlCall(CashCardRepository repo) {
        this.repo = repo;
    }

    public CashCard getCashCardById(Long id, String owner) {
        return repo.findByIdAndOwner(id, owner);
    }
}

