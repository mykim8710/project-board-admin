package io.mykim.projectboardadmin.config.security.service;

import io.mykim.projectboardadmin.adminuser.entity.AdminUser;
import io.mykim.projectboardadmin.adminuser.repository.AdminUserRepository;
import io.mykim.projectboardadmin.config.security.dto.PrincipalDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomPrincipalDetailService implements UserDetailsService {
    private final AdminUserRepository adminUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminUser adminUser = adminUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("존"));
        return new PrincipalDetail(adminUser);
    }
}
