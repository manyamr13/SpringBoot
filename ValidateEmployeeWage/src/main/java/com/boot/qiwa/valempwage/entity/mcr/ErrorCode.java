package com.boot.qiwa.valempwage.entity.mcr;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ESB_ERROR_CODES")
public class ErrorCode {

    @Id
    @Column(name = "Error_ID")
    private Integer Error_ID;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "CODE")
    private String code;

    @Column(name = "EN_DESCRIPTION")
    private String engMsg;

    @Column(name = "AR_DESCRIPTION")
    private String arMsg;
}

