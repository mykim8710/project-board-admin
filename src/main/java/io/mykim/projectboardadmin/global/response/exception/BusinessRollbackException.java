package io.mykim.projectboardadmin.global.response.exception;

import io.mykim.projectboardadmin.global.response.enums.CustomErrorCode;
import lombok.Getter;

@Getter
public class BusinessRollbackException extends RuntimeException {
    private CustomErrorCode errorCode;

    public BusinessRollbackException(CustomErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

}
