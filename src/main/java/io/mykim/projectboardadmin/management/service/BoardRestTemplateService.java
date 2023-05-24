package io.mykim.projectboardadmin.management.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.mykim.projectboardadmin.global.response.dto.CommonResponse;
import io.mykim.projectboardadmin.management.dto.HashtagCreateDto;
import io.mykim.projectboardadmin.management.dto.RequestSearchConditionDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class BoardRestTemplateService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private HttpHeaders getRequestHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return headers;
    }

    public ResponseEntity<CommonResponse> getFindAllArticles(HttpServletRequest request, RequestSearchConditionDto searchConditionDto) {
        final HttpEntity<HttpHeaders> httpEntity = new HttpEntity<>(getRequestHeader());
        String api = "/api/admin/articles";
        URI uri = UriComponentsBuilder
                            .fromUriString(setApiForm(request, api))
                            .queryParam("searchType", "ALL")
                            .queryParam("searchKeyword", searchConditionDto.getSearchKeyword())
                            .queryParam("page", searchConditionDto.getPage())
                            .queryParam("size", searchConditionDto.getSize())
                            .queryParam("sort", searchConditionDto.getSort())
                            .encode()
                            .build()
                            .toUri();

        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, CommonResponse.class);
    }

    public ResponseEntity<CommonResponse> getFindOneArticleById(HttpServletRequest request, Long articleId) {
        final HttpEntity<HttpHeaders> httpEntity = new HttpEntity<>(getRequestHeader());
        String api = "/api/admin/articles/{articleId}";

        URI uri = UriComponentsBuilder
                            .fromUriString(setApiForm(request, api))
                            .encode()
                            .build()
                            .expand(articleId)
                            .toUri();

        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, CommonResponse.class);
    }

    public ResponseEntity<CommonResponse> removeArticleById(HttpServletRequest request, Long articleId) {
        final HttpEntity<HttpHeaders> httpEntity = new HttpEntity<>(getRequestHeader());
        String api = "/api/admin/articles/{articleId}";

        URI uri = UriComponentsBuilder
                            .fromUriString(setApiForm(request, api))
                            .encode()
                            .build()
                            .expand(articleId)
                            .toUri();

        return restTemplate.exchange(uri, HttpMethod.DELETE, httpEntity, CommonResponse.class);
    }

    public ResponseEntity<CommonResponse> getFindAllArticleComments(HttpServletRequest request, RequestSearchConditionDto searchConditionDto) {
        final HttpEntity<HttpHeaders> httpEntity = new HttpEntity<>(getRequestHeader());
        String api = "/api/admin/article-comments";
        URI uri = UriComponentsBuilder
                            .fromUriString(setApiForm(request, api))
                            .queryParam("searchKeyword", searchConditionDto.getSearchKeyword())
                            .queryParam("page", searchConditionDto.getPage())
                            .queryParam("size", searchConditionDto.getSize())
                            .queryParam("sort", searchConditionDto.getSort())
                            .encode()
                            .build()
                            .toUri();

        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, CommonResponse.class);
    }

    public ResponseEntity<CommonResponse> getFindOneArticleCommentById(HttpServletRequest request, Long articleId) {
        final HttpEntity<HttpHeaders> httpEntity = new HttpEntity<>(getRequestHeader());
        String api = "/api/admin/article-comments/{articleId}";

        URI uri = UriComponentsBuilder
                                .fromUriString(setApiForm(request, api))
                                .encode()
                                .build()
                                .expand(articleId)
                                .toUri();

        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, CommonResponse.class);
    }

    public ResponseEntity<CommonResponse> removeArticleCommentById(HttpServletRequest request, Long articleId) {
        final HttpEntity<HttpHeaders> httpEntity = new HttpEntity<>(getRequestHeader());
        String api = "/api/admin/article-comments/{articleId}";

        URI uri = UriComponentsBuilder
                                .fromUriString(setApiForm(request, api))
                                .encode()
                                .build()
                                .expand(articleId)
                                .toUri();

        return restTemplate.exchange(uri, HttpMethod.DELETE, httpEntity, CommonResponse.class);
    }

    public ResponseEntity<CommonResponse> getFindAllHashtags(HttpServletRequest request, RequestSearchConditionDto searchConditionDto) {
        final HttpEntity<HttpHeaders> httpEntity = new HttpEntity<>(getRequestHeader());
        String api = "/api/admin/hashtags";
        URI uri = UriComponentsBuilder
                                .fromUriString(setApiForm(request, api))
                                .queryParam("searchKeyword", searchConditionDto.getSearchKeyword())
                                .queryParam("page", searchConditionDto.getPage())
                                .queryParam("size", searchConditionDto.getSize())
                                .queryParam("sort", searchConditionDto.getSort())
                                .encode()
                                .build()
                                .toUri();

        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, CommonResponse.class);
    }

    public ResponseEntity<CommonResponse> postAddHashtag(HttpServletRequest request, HashtagCreateDto hashtagCreateDto) throws JsonProcessingException {
        final HttpEntity<String> httpEntity = new HttpEntity<>(objectMapper.writeValueAsString(hashtagCreateDto), getRequestHeader());
        String api = "/api/admin/hashtags";
        URI uri = UriComponentsBuilder
                                .fromUriString(setApiForm(request, api))
                                .encode()
                                .build()
                                .toUri();

        try{
            return restTemplate.exchange(uri, HttpMethod.POST, httpEntity, CommonResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return returnErrorResponse(e.getMessage());
        }
    }

    public ResponseEntity<CommonResponse> deleteRemoveHashtag(HttpServletRequest request, Long hashtagId) throws JsonProcessingException {
        final HttpEntity<HttpHeaders> httpEntity = new HttpEntity<>(getRequestHeader());
        String api = "/api/admin/hashtags/{hashtagId}";
        URI uri = UriComponentsBuilder
                            .fromUriString(setApiForm(request, api))
                            .encode()
                            .build()
                            .expand(hashtagId)
                            .toUri();

        try{
            return restTemplate.exchange(uri, HttpMethod.DELETE, httpEntity, CommonResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return returnErrorResponse(e.getMessage());
        }
    }

    public ResponseEntity<CommonResponse> getFindAllServiceUsers(HttpServletRequest request, RequestSearchConditionDto searchConditionDto) {
        final HttpEntity<HttpHeaders> httpEntity = new HttpEntity<>(getRequestHeader());
        String api = "/api/admin/users";
        URI uri = UriComponentsBuilder
                            .fromUriString(setApiForm(request, api))
                            .queryParam("searchKeyword", searchConditionDto.getSearchKeyword())
                            .queryParam("page", searchConditionDto.getPage())
                            .queryParam("size", searchConditionDto.getSize())
                            .queryParam("sort", searchConditionDto.getSort())
                            .encode()
                            .build()
                            .toUri();

        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, CommonResponse.class);
    }

    private ResponseEntity returnErrorResponse(String errorMessage) throws JsonProcessingException {
        String message = StringUtils.removeStart(errorMessage.split(" : ")[1], "\"");
        message = StringUtils.removeEnd(message, "\"");
        CommonResponse response = objectMapper.readValue(message, CommonResponse.class);
        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }

    private String setApiForm(HttpServletRequest request, String api) {
        int localPort = 8080;
        String scheme = request.getScheme();
        String serverName = request.getServerName();

        if(serverName.equals("localhost")) {
            return scheme +"://" +serverName +":" +localPort +api;
        } else{
            return scheme +"://" +serverName +api;
        }
    }
}
