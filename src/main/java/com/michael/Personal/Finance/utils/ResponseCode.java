package com.michael.Personal.Finance.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum ResponseCode {

        API_SUCCESS_STATUS("success", "Request processed successfully"),
        API_FAIL_STATUS("fail", "Failed processing request"),
        API_ERROR_STATUS("error", "Error processing request");

        private String code;
        private String description;

        ResponseCode(String code, String description) {
            this.code = code;
            this.description = description;
        }


}
