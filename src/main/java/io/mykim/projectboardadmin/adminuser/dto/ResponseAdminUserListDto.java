package io.mykim.projectboardadmin.adminuser.dto;

import io.mykim.projectboardadmin.global.pageable.CustomPaginationResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ResponseAdminUserListDto {
    private List<ResponseAdminUserFindDto> responseAdminUserFindDtos;
    private CustomPaginationResponse customPaginationResponse;

    @Builder
    public ResponseAdminUserListDto(List<ResponseAdminUserFindDto> responseAdminUserFindDtos, CustomPaginationResponse customPaginationResponse) {
        this.responseAdminUserFindDtos = responseAdminUserFindDtos;
        this.customPaginationResponse = customPaginationResponse;
    }
}
