package io.mykim.projectboardadmin.global.response.enums;

import lombok.Getter;

@Getter
public enum CustomSuccessCode {
    SIGN_IN_SUCCESS(200, "", "sign in success"),

    ;

    private int status;
    private String code;
    private String message;

    CustomSuccessCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
