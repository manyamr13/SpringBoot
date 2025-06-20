package com.boot.qiwa.valempwage.controller;

import com.boot.qiwa.valempwage.exception.BusinessException;
import com.boot.qiwa.valempwage.model.req.ValEmpWageRq;
import com.boot.qiwa.valempwage.model.res.ValEmpWageRs;
import com.boot.qiwa.valempwage.service.ValEmpWageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/contractmanagement")
@RequiredArgsConstructor
public class ValEmpWageCntrlr {

    private final ValEmpWageService service;

    @PostMapping("/valempwage")
    public ResponseEntity<?> valEmpWage(@RequestBody @Valid ValEmpWageRq request) {
        try {
            ValEmpWageRs response = service.validate(request);
            return ResponseEntity.ok(response);
        } catch (BusinessException ex) {
            return ResponseEntity.badRequest().body(Map.of("errorCode", ex.getCode()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}