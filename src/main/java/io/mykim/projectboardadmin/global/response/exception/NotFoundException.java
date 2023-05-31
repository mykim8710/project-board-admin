package io.mykim.projectboardadmin.global.response.exception;

import io.mykim.projectboardadmin.global.response.enums.CustomErrorCode;
import lombok.Getter;

@Getter
public class NotFoundException extends BusinessRollbackException {
    private CustomErrorCode errorCode;

    public NotFoundException(CustomErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }
}
