package io.mykim.projectboardadmin.adminuser.entity;

import io.mykim.projectboardadmin.global.jpa.AuditingField;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "admin_users")
public class AdminUser extends AuditingField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_user_id")
    private Long id;
    @Column(name = "admin_user_username", length = 255, unique = true, nullable = false)
    private String username;
    @Column(name = "admin_user_password", length = 255, nullable = false)
    private String password;
    @Column(name = "admin_user_nickname", length = 100, unique = true, nullable = false)
    private String nickname;
    @Column(name = "admin_user_email", length = 100, nullable = false)
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(name = "admin_user_role", length = 50)
    private AdminUserRole adminUserRole;

    @Builder
    private AdminUser(String username, String password, String nickname, String email, AdminUserRole adminUserRole) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.adminUserRole = adminUserRole;
    }

    public static AdminUser of(String username, String password, String nickname, String email, AdminUserRole adminUserRole) {
        return AdminUser.builder()
                            .username(username)
                            .password(password)
                            .nickname(nickname)
                            .email(email)
                            .adminUserRole(adminUserRole)
                            .build();
    }

    public void updateEmail(String email) {
        this.email = email;
    }
}
