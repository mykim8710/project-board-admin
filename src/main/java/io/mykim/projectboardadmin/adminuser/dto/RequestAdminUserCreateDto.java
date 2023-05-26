package io.mykim.projectboardadmin.adminuser.dto;

import io.mykim.projectboardadmin.adminuser.entity.AdminUserRole;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class RequestAdminUserCreateDto {
    @NotBlank(message = "아이디를 입력해주세요.")
    private String username;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;
    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;
    private AdminUserRole adminUserRole;

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    @Builder
    public RequestAdminUserCreateDto(String username, String password, String nickname, String email, AdminUserRole adminUserRole) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.adminUserRole = adminUserRole;
    }
}
