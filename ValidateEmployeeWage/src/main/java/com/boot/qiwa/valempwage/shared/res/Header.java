package com.boot.qiwa.valempwage.shared.res;

import com.boot.qiwa.valempwage.entity.mcr.ErrorCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class Header {
    @JsonProperty("TransactionId")
    private String TransactionId;
    @JsonProperty("ChannelId")
    private String ChannelId;
    @JsonProperty("SessionId")
    private String SessionId;
    @Pattern(regexp = "(18|19|20)(\\d{2})[-](0[1-9]|1[012])[-](0[1-9]|[12]\\d|3[01]) ([01]\\d|2[0-3])[:]([0-5]\\d)[:]([0-5]\\d)[.]\\d{0,5}")
    @JsonProperty("RequestTime")
    private String RequestTime;
    @JsonProperty("MWRequestTime")
    private String MWRequestTime;
    @JsonProperty("MWResponseTime")
    private String MWResponseTime;
    @JsonProperty("ServiceCode")
    private String ServiceCode;
    @JsonProperty("DebugFlag")
    private String DebugFlag;
    @JsonProperty("ResponseStatus")
    private ResponseStatus ResponseStatus;

    @Setter
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseStatus {
        @JsonProperty("Status")
        private String Status;
        @JsonProperty("Code")
        private String Code;
        @JsonProperty("ArabicMsg")
        private String ArabicMsg;
        @JsonProperty("EnglishMsg")
        private String EnglishMsg;
    }

    public static Header successRespHeader(com.boot.qiwa.valempwage.shared.req.Header requestHeader, ErrorCode respCode) {
        Header responseHeader = new Header();
        BeanUtils.copyProperties(requestHeader, responseHeader);
        responseHeader.setMWRequestTime(requestHeader.getMwRequestTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        responseHeader.setMWResponseTime(LocalDateTime.now().format(formatter));

        ResponseStatus status = new ResponseStatus();
        status.setStatus(respCode.getStatus());
        status.setCode(respCode.getCode());
        status.setArabicMsg(respCode.getArMsg());
        status.setEnglishMsg(respCode.getEngMsg());

        responseHeader.setResponseStatus(status);
        return responseHeader;
    }
}

