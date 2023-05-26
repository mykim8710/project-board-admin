package io.mykim.projectboardadmin.adminuser.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.mykim.projectboardadmin.adminuser.entity.AdminUser;
import io.mykim.projectboardadmin.adminuser.entity.AdminUserRole;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseAdminUserFindDto {
    private Long id;
    private String username;
    private String nickname;
    private String email;
    private AdminUserRole adminUserRole;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy/MM/dd HH:mm:ss")
    private LocalDateTime lastModifiedAt;

    @Builder
    private ResponseAdminUserFindDto(Long id, String username, String nickname, String email, AdminUserRole adminUserRole, LocalDateTime createdAt, LocalDateTime lastModifiedAt) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.adminUserRole = adminUserRole;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }

    public static ResponseAdminUserFindDto from(AdminUser adminUser) {
        return ResponseAdminUserFindDto
                        .builder()
                        .id(adminUser.getId())
                        .username(adminUser.getUsername())
                        .nickname(adminUser.getNickname())
                        .email(adminUser.getEmail())
                        .adminUserRole(adminUser.getAdminUserRole())
                        .createdAt(adminUser.getCreatedAt())
                        .lastModifiedAt(adminUser.getLastModifiedAt())
                        .build();
    }
}
