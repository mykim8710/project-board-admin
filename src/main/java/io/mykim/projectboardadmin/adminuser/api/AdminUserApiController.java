package io.mykim.projectboardadmin.adminuser.api;

import io.mykim.projectboardadmin.adminuser.dto.AdminUserInfoDuplicateCheckDto;
import io.mykim.projectboardadmin.adminuser.dto.RequestAdminUserCreateDto;
import io.mykim.projectboardadmin.adminuser.service.AdminUserService;
import io.mykim.projectboardadmin.global.response.dto.CommonResponse;
import io.mykim.projectboardadmin.global.response.enums.CustomSuccessCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AdminUserApiController {
    private final AdminUserService adminUserService;

    @GetMapping("/api/v1/admin-users")
    public ResponseEntity<CommonResponse> findAllAdminUserApi(@RequestParam(required = false) String searchKeyword,
                                                              @PageableDefault(page = 0, size = 30, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        log.info("[GET] /api/v1/admin-users?searchKeyword={}&page={}&size={}&sort={} => find all admin users api", searchKeyword, pageable.getOffset(), pageable.getPageSize(), pageable.getSort());
        CommonResponse response = new CommonResponse(CustomSuccessCode.COMMON_SUCCESS, adminUserService.findAllAdminUsers(pageable, searchKeyword));
        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }

    @PostMapping("/api/v1/admin-users")
    public ResponseEntity<CommonResponse> createNewAdminUserApi(@RequestBody @Valid RequestAdminUserCreateDto createDto) {
        log.info("[POST] /api/v1/admin-users => create new admin users apiRequestAdminUserCreateDto={}", createDto);
        CommonResponse response = new CommonResponse(CustomSuccessCode.INSERT_SUCCESS, adminUserService.createNewAdminUser(createDto));
        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }

    @GetMapping("/api/v1/admin-users/duplicate-check")
    public ResponseEntity<CommonResponse> duplicateCheckAdminUserInfoApi(@ModelAttribute AdminUserInfoDuplicateCheckDto duplicateCheckDto) {
        log.info("[GET] /api/v1/admin-users/duplicate-check?type={}&keyword={}  =>  duplicate Check UserInfo api", duplicateCheckDto.getType(), duplicateCheckDto.getKeyword());
        CommonResponse response = adminUserService.duplicateCheck(duplicateCheckDto);
        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }

}
