package io.mykim.projectboardadmin.adminuser.dto;

import io.mykim.projectboardadmin.adminuser.entity.AdminUser;
import io.mykim.projectboardadmin.adminuser.entity.AdminUserRole;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseAdminUserFindDto {
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private AdminUserRole adminUserRole;

    @Builder
    private ResponseAdminUserFindDto(Long id, String username, String nickname, String email, AdminUserRole adminUserRole) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.adminUserRole = adminUserRole;
    }

    public static ResponseAdminUserFindDto from(AdminUser adminUser) {
        return ResponseAdminUserFindDto
                        .builder()
                        .id(adminUser.getId())
                        .username(adminUser.getUsername())
                        .nickname(adminUser.getNickname())
                        .email(adminUser.getEmail())
                        .adminUserRole(adminUser.getAdminUserRole())
                        .build();
    }
}
