package io.mykim.projectboardadmin.management.api;

import io.mykim.projectboardadmin.global.response.dto.CommonResponse;
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
@RequestMapping("/api/v1/management/article-comments")
public class ArticleCommentManagementApiController {
    private final BoardRestTemplateService boardServiceRestTemplate;

    // 댓글 목록
    @GetMapping
    public ResponseEntity<CommonResponse> findAllArticleComments(HttpServletRequest request, @ModelAttribute RequestSearchConditionDto searchConditionDto) {
        log.info("[GET] /api/v1/management/article-comments?searchKeyword={}&page={}&size={}&sort={}", searchConditionDto.getSearchKeyword(), searchConditionDto.getPage(), searchConditionDto.getSize(), searchConditionDto.getSort());
        return boardServiceRestTemplate.getFindAllArticleComments(request, searchConditionDto);
    }

    // 댓글 단건 조회
    @GetMapping("/{articleCommentId}")
    public ResponseEntity<CommonResponse> findOneArticleCommentById(HttpServletRequest request, @PathVariable Long articleCommentId) {
        log.info("[GET] /api/v1/management/article-comments/{}", articleCommentId);
        return boardServiceRestTemplate.getFindOneArticleCommentById(request, articleCommentId);
    }

    // 댓글 삭제
    @DeleteMapping("/{articleCommentId}")
    public ResponseEntity<CommonResponse> removeArticleCommentById(HttpServletRequest request, @PathVariable Long articleCommentId) {
        log.info("[DELETE] /api/v1/management/article-comments/{}", articleCommentId);
        return boardServiceRestTemplate.removeArticleCommentById(request, articleCommentId);
    }


}
