package com.boot.qiwa.valempwage.model.spresp;

import com.boot.qiwa.valempwage.service.AllowancesList;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import lombok.Data;

import java.io.StringReader;
import java.math.BigDecimal;
@Data
public class finDetailsRs {

    private Integer LaborerId;
    private String Salary;
    private Integer SalaryTypeId;
    private Integer SalaryFrequency;
    private String LaborerMobileNumber;
    private String LaborerEmail;
    private Integer NationalityId;
    private Integer StatusId;
    private Long UnifiedNationalNumber;
    private Integer EstablishmentId;
    private String GOSIBasicSalary;
    private String GOSITotalSalary;
    private String TotalSalary;
    private Integer RelatedToId;
    private AllowancesList Allowance;

    public static finDetailsRs from(Object[] row) throws Exception {
        finDetailsRs finDetRs = new finDetailsRs();
        finDetRs.setLaborerId((Integer) row[0]);
        finDetRs.setSalary((String) row[1] != null ? row[1].toString() : null);
        finDetRs.setSalaryTypeId((Integer) row[2]);
        finDetRs.setSalaryFrequency((Integer) row[3]);
        finDetRs.setLaborerMobileNumber((String) row[4]);
        finDetRs.setLaborerEmail((String) row[5]);
        finDetRs.setNationalityId((Integer) row[6]);
        finDetRs.setStatusId((Integer) row[7]);
        finDetRs.setUnifiedNationalNumber((Long) row[8]);
        finDetRs.setEstablishmentId((Integer) row[9]);
        finDetRs.setGOSIBasicSalary(row[10] != null ? row[10].toString() : null);
        finDetRs.setGOSITotalSalary(row[11] != null ? row[11].toString() : null);
        finDetRs.setTotalSalary(row[12] != null ? row[12].toString() : null);
        finDetRs.setRelatedToId((Integer) row[13]);
        if (row[14] != null) {
            String allowanceXml = (String) row[14];
            AllowancesList allowancesList = parseXml(allowanceXml);
            finDetRs.setAllowance(allowancesList);
        }
        return finDetRs;
    }
    public static AllowancesList parseXml(String xml) throws Exception {
        JAXBContext context = JAXBContext.newInstance(AllowancesList.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (AllowancesList) unmarshaller.unmarshal(new StringReader(xml));
    }
}
