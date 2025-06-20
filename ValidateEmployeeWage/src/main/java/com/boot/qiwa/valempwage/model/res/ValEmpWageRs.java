package com.boot.qiwa.valempwage.model.res;

import com.boot.qiwa.valempwage.shared.res.Header;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ValEmpWageRs {
    @NotNull
    @JsonProperty("ValidateEmployeeWageRs")
    private ValidateEmployeeWageRs ValidateEmployeeWageRs;

    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class ValidateEmployeeWageRs {
        @JsonProperty("Header")
        private Header Header;
        @JsonProperty("Body")
        private Object Body;

        @Setter
        @Data
        @AllArgsConstructor
        public static class Body {
            private BigDecimal CurrentBasicSalary;
            private BigDecimal GosiBasicSalary;
            private BigDecimal CurrentTotalSalary;
            private BigDecimal GosiTotalSalary;
            private BigDecimal NewTotalSalary;
            private BigDecimal OtherAllowances;
            private Integer EstablishmentId;
            private Integer UnifiedNationalNumber;
            private String LaborerMobileNumber;
            private String LaborerEmail;
            private Integer NationalityId;
        }
    }
}