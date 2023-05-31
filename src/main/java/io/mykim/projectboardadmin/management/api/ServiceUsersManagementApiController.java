package io.mykim.projectboardadmin.management.api;

import io.mykim.projectboardadmin.global.response.dto.CommonResponse;
import io.mykim.projectboardadmin.management.dto.RequestSearchConditionDto;
import io.mykim.projectboardadmin.management.service.BoardRestTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/management/service-users")
public class ServiceUsersManagementApiController {
    private final BoardRestTemplateService boardServiceRestTemplate;

    // 게시판 서비스 회원 목록
    @GetMapping
    public ResponseEntity<CommonResponse> findAllArticleComments(HttpServletRequest request, @ModelAttribute RequestSearchConditionDto searchConditionDto) {
        log.info("[GET] /api/v1/management/service-users?searchKeyword={}&page={}&size={}&sort={}", searchConditionDto.getSearchKeyword(), searchConditionDto.getPage(), searchConditionDto.getSize(), searchConditionDto.getSort());
        return boardServiceRestTemplate.getFindAllServiceUsers(request, searchConditionDto);
    }

}
