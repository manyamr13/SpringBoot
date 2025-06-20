package com.boot.cashcard.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CashCard {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public Double amount;
    public String owner;
}