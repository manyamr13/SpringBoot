package com.boot.qiwa.valempwage.service;
import com.boot.qiwa.valempwage.config.PropValues;
import com.boot.qiwa.valempwage.entity.main.ValEmpWageEntity;
import com.boot.qiwa.valempwage.entity.mcr.ErrorCode;
import com.boot.qiwa.valempwage.exception.BusinessException;
import com.boot.qiwa.valempwage.model.req.ValEmpWageRq;
import com.boot.qiwa.valempwage.model.res.ValEmpWageRs;
import com.boot.qiwa.valempwage.model.res.ValEmpWageRs.ValidateEmployeeWageRs;
import com.boot.qiwa.valempwage.model.spresp.finDetailsRs;
import com.boot.qiwa.valempwage.repository.mcr.ErrorCodeRepo;
import com.boot.qiwa.valempwage.repository.main.ValEmpWageRepo;
import com.boot.qiwa.valempwage.shared.res.Header;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;

import java.io.StringReader;
import java.util.Objects;


//@Component
@Service
@RequiredArgsConstructor
@Data
public class ValEmpWageService {


    public PropValues propVal;

//    public ValEmpWageRq valEmpWageRq;

    private final ValEmpWageRepo repo;
    private final ErrorCodeRepo mcrRepo;


//    private final LaborerRepository laborerRepository;
//    private final TerminateContractRepository terminateRepository;
//    private final SponsorChangeRepository sponsorChangeRepository;
//    private final FinancialCalculationService calculationService;

    @Transactional
    public ValEmpWageRs validate(ValEmpWageRq request) throws Exception {
        String errCode = "";
        var reqBody = request.getValidateEmployeeWageRq().getBody();
        BigDecimal basicSalary = reqBody.getNewFinancialDetails().getBasicSalary();

        List<Object[]> res = repo.Contracts_FinancialDetails_Get(reqBody.getContractId());
        finDetailsRs finDetRs = finDetailsRs.from(res.get(0));

        if (res.get(0) == null) {
            errCode = propVal.getErrorCodes().getFinDetErr(); //E0001195
        }
        else if (!Objects.equals(finDetRs.getStatusId(), propVal.getProperties().getStatusIdFifteen())){
            errCode = propVal.getErrorCodes().getSalaryErr(); //E0000732
        }else if (!Objects.equals(finDetRs.getSalaryTypeId(), propVal.getProperties().getSalaryTypeIdOne())){
            errCode = propVal.getErrorCodes().getSalaryTypeIdErr(); //E0003133
        }else if (basicSalary.compareTo(new BigDecimal(finDetRs.getSalary())) <= 0){
            errCode = propVal.getErrorCodes().getSalaryErr(); //E0003129
        }else if (basicSalary.compareTo(new BigDecimal(finDetRs.getGOSIBasicSalary())) <= 0){
            errCode = propVal.getErrorCodes().getGosiSalaryErr(); //E0003144
        }else if
        (reqBody.getNewFinancialDetails().getHousingAllowance() != null &&
            reqBody.getNewFinancialDetails().getTransportationAllowance() != null &&
            finDetRs.getGOSIBasicSalary() != null &&
            new BigDecimal(finDetRs.getGOSIBasicSalary()).compareTo(BigDecimal.ZERO) <= 0){
            errCode = propVal.getErrorCodes().getGosiSalaryErr(); //E0003127
        }

        ErrorCode respCode = mcrRepo.findByCode(errCode);
        ValEmpWageRs resp = new ValEmpWageRs();
        ValidateEmployeeWageRs valEmpRs = new ValidateEmployeeWageRs();
        valEmpRs.setHeader(Header.successRespHeader(request.getValidateEmployeeWageRq().getHeader(),respCode));
        resp.setValidateEmployeeWageRs(valEmpRs);
        return resp;


 /*       // Example validation
        if (request.getNewFinancialDetails().getBasicSalary().compareTo(contractDetails.getSalary()) <= 0)
            throw new BusinessException("E0003129");

        // More validations...

        var laborerInfo = (List.of(5, 7, 8, 9).contains(contractDetails.getRelatedToId()))
                ? null
                : laborerRepository.getLaborerInfo(request.getEmployeeIdNo());

        if (laborerInfo != null && laborerInfo.getLaborerStatusId() != 1)
            throw new BusinessException("E0003128");

        var hasPendingTC = terminateRepository.hasPendingRequest(
                request.getLaborOfficeId(),
                request.getSequenceNumber(),
                request.getEmployeeIdNo(),
                request.getContractId()
        );
        if (hasPendingTC) throw new BusinessException("E0001618");

        var hasPendingCS = (!List.of(5, 7).contains(contractDetails.getRelatedToId())) &&
                sponsorChangeRepository.hasPendingSponsorChange(request.getEmployeeIdNo());

        if (hasPendingCS) throw new BusinessException("E0000570");

        var calculated = calculationService.calculateTotalSalary(request, contractDetails);
        if (calculated.getNotes() != null) throw new BusinessException("E0003140");

        // Final validations...

        return calculated.toResponse();*/
//        return null;
    }

}
/*   String LaborerId = (String) finDetRs[0],
                Salary = (String) finDetRs[1],
                LaborerMobileNumber = (String) finDetRs[4],
                LaborerEmail = (String) finDetRs[5],
                GOSIBasicSalary = (String) finDetRs[10],
                GOSITotalSalary = (String) finDetRs[11],
                TotalSalary = (String) finDetRs[12],
                Allowance = (String) finDetRs[14];

        int SalaryTypeId = (Integer) finDetRs[2],
                SalaryFrequency = (Integer) finDetRs[3],
                NationalityId = (Integer) finDetRs[6],
                StatusId = (Integer) finDetRs[7],
                RelatedToId = (Integer) finDetRs[13];
        long UnifiedNationalNumber = (Integer) finDetRs[8],
                EstablishmentId = (Integer) finDetRs[9];*/