package com.boot.qiwa.valempwage.shared.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Header {

        @JsonProperty("TransactionId")
        private String transactionId;

        @JsonProperty("ChannelId")
        private String channelId;

        @JsonProperty("SessionId")
        private String sessionId;

        @Pattern(regexp = "(18|19|20)(\\d{2})[-](0[1-9]|1[012])[-](0[1-9]|[12]\\d|3[01]) ([01]\\d|2[0-3])[:]([0-5]\\d)[:]([0-5]\\d)[.]\\d{0,5}")
        @JsonProperty("RequestTime")
        private String requestTime;

        @JsonProperty("MWRequestTime")
        private String mwRequestTime;

        @JsonProperty("ServiceCode")
        private String serviceCode;

        @JsonProperty("DebugFlag")
        @Pattern(regexp = "[01]", message = "DebugFlag must be 0 or 1")
        private String DebugFlag;
        @JsonProperty("UserInfo")
        private  UserInfo UserInfo;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class UserInfo {
                @JsonProperty("UserId")
                private String UserId;
                @JsonProperty("IDNumber")
                private String IDNumber;
        }
}
