package com.boot.qiwa.valempwage.repository.mcr;

import com.boot.qiwa.valempwage.entity.mcr.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorCodeRepo extends JpaRepository<ErrorCode, String> {
    ErrorCode findByCode(String code);
}
