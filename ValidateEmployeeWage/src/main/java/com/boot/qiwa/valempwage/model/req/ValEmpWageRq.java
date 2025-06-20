package com.boot.qiwa.valempwage.model.req;

import com.boot.qiwa.valempwage.shared.req.Header;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValEmpWageRq {
    @JsonProperty("ValidateEmployeeWageRq")
    private ValidateEmployeeWageRq ValidateEmployeeWageRq;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class ValidateEmployeeWageRq {

        @JsonProperty("Header")
        private Header Header;
        @JsonProperty("Body")
        private Body Body;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Body {

            @NotNull
            @JsonProperty("LaborOfficeId")
            private Integer LaborOfficeId;
            @NotNull
            @JsonProperty("SequenceNumber")
            private Integer SequenceNumber;
            @NotNull
            @JsonProperty("EmployeeIdNo")
            private Long EmployeeIdNo;
            @NotNull
            @JsonProperty("ContractId")
            private Integer ContractId;
            @NotNull
            @JsonProperty("NewFinancialDetails")
            private FinancialDetails NewFinancialDetails;

            @Data
            @AllArgsConstructor
            @NoArgsConstructor
            public static class FinancialDetails {
                @NotNull
                @JsonProperty("BasicSalary")
                private BigDecimal BasicSalary;
                @JsonProperty("HousingAllowance")
                private BigDecimal HousingAllowance;
                @JsonProperty("TransportationAllowance")
                private BigDecimal TransportationAllowance;
            }
        }
    }
}