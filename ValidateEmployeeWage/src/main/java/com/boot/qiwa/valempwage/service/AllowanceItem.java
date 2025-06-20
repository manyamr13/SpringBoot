package com.boot.qiwa.valempwage.service;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import lombok.Data;

import java.math.BigDecimal;
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class AllowanceItem {

    private String NameEn;
    private String NameAr;
    private int AllowanceTypeId;
    private String AllowanceTypeAr;
    private String AllowanceTypeEn;
    private int AmountTypeId;
    private String AmountTypeAr;
    private String AmountTypeEn;
    private int FrequencyId;
    private String FrequencyAr;
    private String FrequencyEn;
    private BigDecimal Amount;
    private int IsProvidedByEstablishment;

    // Getters and setters
}

