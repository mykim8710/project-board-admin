package io.mykim.projectboardadmin.management.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.mykim.projectboardadmin.global.response.dto.CommonResponse;
import io.mykim.projectboardadmin.management.dto.HashtagCreateDto;
import io.mykim.projectboardadmin.management.dto.RequestSearchConditionDto;
import io.mykim.projectboardadmin.management.service.BoardRestTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/management/hashtags")
public class HashtagManagementApiController {
    private final BoardRestTemplateService boardServiceRestTemplate;

    // 해시태그 목록
    @GetMapping
    public ResponseEntity<CommonResponse> findAllArticleComments(HttpServletRequest request, @ModelAttribute RequestSearchConditionDto searchConditionDto) {
        log.info("[GET] /api/v1/management/hashtags?searchKeyword={}&page={}&size={}&sort={}", searchConditionDto.getSearchKeyword(), searchConditionDto.getPage(), searchConditionDto.getSize(), searchConditionDto.getSort());
        return boardServiceRestTemplate.getFindAllHashtags(request, searchConditionDto);
    }

    // 해시태그 추가
    @PostMapping
    public ResponseEntity<CommonResponse> findOneArticleCommentById(HttpServletRequest request, @RequestBody HashtagCreateDto hashtagCreateDto) throws JsonProcessingException {
        log.info("[POST] /api/v1/management/hashtags > HashtagCreateDto : {}", hashtagCreateDto);
        return boardServiceRestTemplate.postAddHashtag(request, hashtagCreateDto);
    }

    // 해시태그 삭제
    @DeleteMapping("/{hashtagId}")
    public ResponseEntity<CommonResponse> removeArticleCommentById(HttpServletRequest request, @PathVariable Long hashtagId) throws JsonProcessingException {
        log.info("[DELETE] /api/v1/management/hashtags/{}", hashtagId);
        return boardServiceRestTemplate.deleteRemoveHashtag(request, hashtagId);
    }


}
