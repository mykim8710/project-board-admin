package io.mykim.projectboardadmin.config.response.dto;

import io.mykim.projectboardadmin.config.response.enums.CustomErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CommonResponse<T> {
    private int status;
    private String code;
    private String message;
    private T data;

    public CommonResponse(CustomErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message =errorCode.getMessage();
    }

    public CommonResponse(CustomErrorCode errorCode, T data) {
        this.status = errorCode.getStatus();
        this.code = errorCode.getCode();
        this.message =errorCode.getMessage();
        this.data = data;
    }

}

