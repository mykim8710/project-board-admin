package io.mykim.projectboardadmin.adminuser.entity;

import io.mykim.projectboardadmin.config.jpa.AuditingField;
import lombok.AccessLevel;
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
    @Column(name = "admin_user_name", length = 255, unique = true, nullable = false)
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


}