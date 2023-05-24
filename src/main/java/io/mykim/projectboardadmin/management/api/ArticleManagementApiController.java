package io.mykim.projectboardadmin.management.api;

import io.mykim.projectboardadmin.global.response.dto.CommonResponse;
import io.mykim.projectboardadmin.management.dto.RequestSearchConditionDto;
import io.mykim.projectboardadmin.management.service.BoardRestTemplateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/management/articles")
public class ArticleManagementApiController {
    private final BoardRestTemplateService boardServiceRestTemplate;

    // 게시글 목록
    @GetMapping
    public ResponseEntity<CommonResponse> findAllArticles(HttpServletRequest request, @ModelAttribute RequestSearchConditionDto searchConditionDto) {
        log.info("[GET] /api/v1/management/articles?searchKeyword={}&page={}&size={}&sort={}", searchConditionDto.getSearchKeyword(), searchConditionDto.getPage(), searchConditionDto.getSize(), searchConditionDto.getSort());
        return boardServiceRestTemplate.getFindAllArticles(request, searchConditionDto);
    }

    // 게시글 단건 조회
    @GetMapping("/{articleId}")
    public ResponseEntity<CommonResponse> findOneArticleById(HttpServletRequest request, @PathVariable Long articleId) {
        log.info("[GET] /api/v1/management/articles/{}", articleId);
        return boardServiceRestTemplate.getFindOneArticleById(request, articleId);
    }

    // 게시글 삭제
    @DeleteMapping("/{articleId}")
    public ResponseEntity<CommonResponse> removeArticleById(HttpServletRequest request, @PathVariable Long articleId) {
        log.info("[DELETE] /api/v1/management/articles/{}", articleId);
        return boardServiceRestTemplate.removeArticleById(request, articleId);
    }


}
