package com.michael.Personal.Finance.email;

import lombok.Getter;

@Getter


public enum EmailServiceTemplate {

    ACTIVATE_ACCOUNT("activate_account"),
    CONFIRM_EMAIL("confirm-email"),
    RESET_PASSWORD("reset-password"),
    WELCOME_EMAIL("welcome-email");


    private final String name;
    EmailServiceTemplate( String name) {
        this.name = name;
    }


}
