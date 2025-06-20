package com.boot.qiwa.valempwage.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
@Data
public class PropValues {

    private ErrorCodes errorCodes;
    private Properties properties;


    @Data
    public static class ErrorCodes {
        private String finDetErr;
        private String statusIdErr;
        private String salaryTypeIdErr;
        private String salaryErr;
        private String gosiSalaryErr;
        private String TransHousingErr;

    }

    @Data
    public static class Properties {
        private Integer salaryTypeIdOne;
        private Integer statusIdFifteen;
    }
}