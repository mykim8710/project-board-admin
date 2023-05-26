package io.mykim.projectboardadmin.adminuser.repository;

import io.mykim.projectboardadmin.adminuser.entity.AdminUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminUserQuerydslRepository {
    Page<AdminUser> findAllAdminUsers(Pageable pageable, String searchKeyword);
}
