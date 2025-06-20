package com.boot.qiwa.valempwage.repository.main;

import com.boot.qiwa.valempwage.entity.main.ValEmpWageEntity;
import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValEmpWageRepo extends JpaRepository<ValEmpWageEntity, Long> {

    @Procedure
    List<Object[]> Contracts_FinancialDetails_Get(@Param("ContractId") Integer ContractId);


}