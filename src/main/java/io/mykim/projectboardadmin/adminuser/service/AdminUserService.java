package io.mykim.projectboardadmin.adminuser.service;

import io.mykim.projectboardadmin.adminuser.dto.AdminUserInfoDuplicateCheckDto;
import io.mykim.projectboardadmin.adminuser.dto.RequestAdminUserCreateDto;
import io.mykim.projectboardadmin.adminuser.dto.ResponseAdminUserFindDto;
import io.mykim.projectboardadmin.adminuser.dto.ResponseAdminUserListDto;
import io.mykim.projectboardadmin.adminuser.entity.AdminUser;
import io.mykim.projectboardadmin.adminuser.repository.AdminUserRepository;
import io.mykim.projectboardadmin.global.pageable.CustomPaginationResponse;
import io.mykim.projectboardadmin.global.response.dto.CommonResponse;
import io.mykim.projectboardadmin.global.response.enums.CustomErrorCode;
import io.mykim.projectboardadmin.global.response.enums.CustomSuccessCode;
import io.mykim.projectboardadmin.global.response.exception.DuplicateException;
import io.mykim.projectboardadmin.global.response.exception.NotValidRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminUserService {
    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public ResponseAdminUserListDto findAllAdminUsers(Pageable pageable, String searchKeyword) {
        Page<AdminUser> allAdminUsers = adminUserRepository.findAllAdminUsers(pageable, searchKeyword);

        List<ResponseAdminUserFindDto> responseAdminUserFindDtos = allAdminUsers.getContent()
                                                                                .stream()
                                                                                .map(adminUser -> ResponseAdminUserFindDto.from(adminUser))
                                                                                .collect(Collectors.toList());
        return ResponseAdminUserListDto
                .builder()
                .responseAdminUserFindDtos(responseAdminUserFindDtos)
                .customPaginationResponse(CustomPaginationResponse.of(allAdminUsers.getTotalElements(), allAdminUsers.getTotalPages(), allAdminUsers.getNumber()))
                .build();
    }

    @Transactional
    public Long createNewAdminUser(RequestAdminUserCreateDto createDto) {
        createDto.encodePassword(passwordEncoder);
        AdminUser adminUser = AdminUser.of(createDto);
        return adminUserRepository.save(adminUser).getId();
    }

    @Transactional(readOnly = true)
    public CommonResponse duplicateCheck(AdminUserInfoDuplicateCheckDto duplicateCheckDto) {
        // username, nickname
        CommonResponse response = new CommonResponse(CustomSuccessCode.COMMON_SUCCESS);

        switch (duplicateCheckDto.getType()) {
            case "username":
                Boolean existsByUsername = adminUserRepository.existsByUsername(duplicateCheckDto.getKeyword());
                if(existsByUsername) {
                    throw new DuplicateException(CustomErrorCode.DUPLICATE_ADMIN_USER_USERNAME);
                }
                break;
            case "nickname":
                Boolean existsByNickname = adminUserRepository.existsByNickname(duplicateCheckDto.getKeyword());
                if(existsByNickname) {
                    throw new DuplicateException(CustomErrorCode.DUPLICATE_ADMIN_USER_NICKNAME);
                }
                break;

            default:
                throw new NotValidRequestException(CustomErrorCode.NOT_VALID_REQUEST);
        }

        return response;
    }
}
