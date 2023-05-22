package io.mykim.projectboardadmin.config.response.enums;

import lombok.Getter;

@Getter
public enum CustomErrorCode {
    NOT_VALID_ACCOUNT(400, "U001", "check your account or password"),
    UN_AUTHORIZED_USER(401, "U002", "You need authentication"),
    ACCESS_DENIED(403, "U003", "Access denied."),

    ;

    private int status;
    private String code;
    private String message;

    CustomErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
