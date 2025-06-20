package com.boot.qiwa.valempwage.entity.main;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ValEmpWageEntity {
    @Id
    private Long Id;

//    public void finDetailsRs(){
//        super();
//    }

//    public void finDetailsRs(Integer LaborerId, BigDecimal Salary, Integer SalaryTypeId, Integer SalaryFrequency, String LaborerMobileNumber,
//                             String LaborerEmail, Integer NationalityId, Integer StatusId, String UnifiedNationalNumber, Long EstablishmentId, BigDecimal GOSIBasicSalary,
//                             BigDecimal GOSITotalSalary, BigDecimal TotalSalary, Integer RelatedToId, String Allowance) {
//
//    }
//    super();
}
