package io.mykim.projectboardadmin.global.response.exception;

import io.mykim.projectboardadmin.global.response.enums.CustomErrorCode;
import lombok.Getter;

@Getter
public class NotFoundExceptionException extends BusinessRollbackException {
    private CustomErrorCode errorCode;

    public NotFoundExceptionException(CustomErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }
}
