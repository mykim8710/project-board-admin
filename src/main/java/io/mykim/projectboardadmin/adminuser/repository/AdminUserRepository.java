package io.mykim.projectboardadmin.adminuser.repository;

import io.mykim.projectboardadmin.adminuser.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminUserRepository extends JpaRepository<AdminUser, Long> {
}
