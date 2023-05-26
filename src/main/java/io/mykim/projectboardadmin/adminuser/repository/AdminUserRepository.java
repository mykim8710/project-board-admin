package io.mykim.projectboardadmin.adminuser.repository;

import io.mykim.projectboardadmin.adminuser.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminUserRepository extends JpaRepository<AdminUser, Long>, AdminUserQuerydslRepository {
    Optional<AdminUser> findByUsername(String username);
    Boolean existsByNickname(String nickname);
    Boolean existsByUsername(String username);
}
