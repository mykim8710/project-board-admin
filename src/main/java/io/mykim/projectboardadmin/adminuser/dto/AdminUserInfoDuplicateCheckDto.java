package io.mykim.projectboardadmin.adminuser.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AdminUserInfoDuplicateCheckDto {
    private String keyword;
    private String type;

    @Builder
    public AdminUserInfoDuplicateCheckDto(String keyword, String type) {
        this.keyword = keyword;
        this.type = type;
    }
}
