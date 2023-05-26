package io.mykim.projectboardadmin.global.response.enums;

import lombok.Getter;

@Getter
public enum CustomSuccessCode {
    SIGN_IN_SUCCESS(200, "", "sign in success"),
    COMMON_SUCCESS(200, "", "SUCCESS"),
    INSERT_SUCCESS(200, "", "INSERT SUCCESS"),
    UPDATE_SUCCESS(200, "", "UPDATE SUCCESS"),
    DELETE_SUCCESS(200, "", "DELETE SUCCESS"),

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
