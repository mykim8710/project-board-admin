package io.mykim.projectboardadmin.jpa;

import io.mykim.projectboardadmin.adminuser.entity.AdminUser;
import io.mykim.projectboardadmin.adminuser.entity.AdminUserRole;
import io.mykim.projectboardadmin.adminuser.repository.AdminUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JPA - DB 연결 테스트")
@DataJpaTest
class JpaRepositoryTest {
    @Autowired
    private AdminUserRepository adminUserRepository;

    @Test
    @DisplayName("관리자 사용자 select 테스트")
    void jpa_db_admin_user_select_test() throws Exception {
        // given
        int size = 10;
        List<AdminUser> insertAdminUsers = IntStream.range(1, size+1)
                                                    .mapToObj(i -> AdminUser.of("username_" + i, "password", "nickname_" + i, "email", AdminUserRole.ROLE_MASTER))
                                                    .collect(Collectors.toList());

        adminUserRepository.saveAll(insertAdminUsers);


        // when
        List<AdminUser> adminUsers = adminUserRepository.findAll();

        // then
        assertThat(adminUsers)
                            .isNotNull()
                            .hasSize(size);
    }

    @Test
    @DisplayName("관리자 사용자 insert 테스트")
    void jpa_db_admin_user_insert_test() throws Exception {
        // given
        AdminUser of = AdminUser.of("username", "password", "nickname", "email", AdminUserRole.ROLE_MASTER);

        // when
        adminUserRepository.save(of);

        // then
        assertThat(adminUserRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("관리자 사용자 update 테스트")
    void jpa_db_admin_user_update_test() throws Exception {
        // given
        AdminUser of = AdminUser.of("username", "password", "nickname", "email", AdminUserRole.ROLE_MASTER);
        adminUserRepository.saveAndFlush(of);

        // when
        AdminUser findAdminUser = adminUserRepository.findById(of.getId()).get();
        String updateEmail = "updateEmail";
        findAdminUser.updateEmail(updateEmail);
        adminUserRepository.flush();

        // then
        assertThat(findAdminUser).hasFieldOrPropertyWithValue("username", "username");
        assertThat(findAdminUser).hasFieldOrPropertyWithValue("email", updateEmail);
    }

    @Test
    @DisplayName("관리자 사용자 delete 테스트")
    void jpa_db_admin_user_delete_test() throws Exception {
        // given
        AdminUser of = AdminUser.of("username", "password", "nickname", "email", AdminUserRole.ROLE_MASTER);
        adminUserRepository.saveAndFlush(of);

        // when
        adminUserRepository.delete(of);

        // then
        Assertions.assertThat(adminUserRepository.count()).isEqualTo(0);
    }
}