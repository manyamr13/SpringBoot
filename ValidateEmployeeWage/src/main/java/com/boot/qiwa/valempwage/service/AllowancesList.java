package com.boot.qiwa.valempwage.service;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

import java.util.List;

@XmlRootElement(name = "AllowancesList")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class AllowancesList {

    @XmlElement(name = "AllowanceItem")
    private List<AllowanceItem> allowanceItems;

    // Getters and setters
}

