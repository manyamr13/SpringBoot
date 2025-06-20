package com.boot.qiwa.valempwage.exception;

public class BusinessException extends RuntimeException {
    private final String code;

    public BusinessException(String code) {
        super(code);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
