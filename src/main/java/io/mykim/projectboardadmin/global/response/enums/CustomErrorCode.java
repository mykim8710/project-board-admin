package io.mykim.projectboardadmin.global.response.enums;

import lombok.Getter;

@Getter
public enum CustomErrorCode {
    // common
    NOT_VALID_REQUEST(400, "G001", "not validation request"),
    // validation error
    VALIDATION_ERROR(400, "V001", "validation error"),


    NOT_VALID_ACCOUNT(400, "U001", "check your account or password"),
    UN_AUTHORIZED_USER(401, "U002", "You need authentication"),
    ACCESS_DENIED(403, "U003", "Access denied."),

    DUPLICATE_ADMIN_USER_USERNAME(400, "D001", "duplicate this username"),
    DUPLICATE_ADMIN_USER_NICKNAME(400, "D002", "duplicate this nickname"),


    NOT_FOUND_TODO(400, "NF001", "not found this todo"),
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
