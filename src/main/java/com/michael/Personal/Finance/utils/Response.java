package com.michael.Personal.Finance.utils;

import lombok.Getter;

@Getter
public enum Response {

        SUCCESS("00", "Approved or completed successfully"),
        FAILED("99", "Unsuccessful transaction"),
        UNKNOWN("01", "Status unknown, please wait for settlement report"),
        INVALID_ACCOUNT("07", "Request processing in progress"),
        REQUEST_PROCESSING("09", "Status unknown, please wait for settlement report"),
        INVALID_TRANSACTION("12", "Invalid transaction"),

        INVALID_BANK_CODE("16", "Unknown Bank Code"),
        NO_ACTION("21", "No action is taken"),
        IN_SUFFICIENT_FUNDS("51", "No sufficient funds"),
        ACCOUNT_BLOCK("69", "Unsuccessful Account/Amount block"),

        LIMIT_EXCEEDED("61", "Transfer limit Exceeded"),
        DAILY_LIMIT_EXCEEDED("61", "Daily limit Exceeded"),
        BALANCE_LIMIT_EXCEEDED("61", "Deposit/Balance limit Exceeded"),
        ACCOUNT_UNBLOCK("70", "Unsuccessful Account/Amount unblock"),
        DUPLICATE_REQUEST("94", "Duplicate request or transaction"),
        SYSTEM_MALFUNCTION("96", "System malfunction"),
        TIMEOUT("97", "Timeout waiting for a response from destination"),
        ACCOUNT_ALREADY_EXITS("98", "Account already exists"),
        ACCOUNT_ALREADY_IN_USE("105", "Account already in use"),
        INVALID_SOURCE_ACCOUNT("99", "Invalid Source Account"),
        INVALID_BENEFICIARY_ACCOUNT("100", "Invalid Beneficiary Account"),
        SOURCE_ACCOUNT_BLOCK("101", "Unsuccessful Source Account/Amount block"),
        BENEFICIARY_ACCOUNT_BLOCK("102", "Unsuccessful Beneficiary Account/Amount block"),
        DEBIT_OK("103", "Debit ok"),
        INVALID_DATE_FORMAT("104", "Invalid Date format"),
        CUSTOMER_ACCOUNT_RECORD_MISMATCH("105", "Customer account mis match"),
        INVALID_OTP("106", "Invalid otp"),
        INVALID_ACTIVATION_KEY("107", "Invalid activation key"),
        PROFILE_ALREADY_ACTIVATED_KEY("108", "Profile already activated"),
        INVALID_PROFILE("109", "Invalid Profile"),
        DUPLICATE_RECORD("110", "Duplicate record"),
        INVALID_AUTHORITIES("402", "Invalid Authorities"),
    ;

      private String responseCode;

      private String responseMessage;
      Response(String responseCode, String responseMessage) {
    }
}
