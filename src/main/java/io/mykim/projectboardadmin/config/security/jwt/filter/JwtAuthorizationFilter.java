package io.mykim.projectboardadmin.config.security.jwt.filter;


// Authorization :  권한부여, 액세스 제어로 사용자가 읽기, 수정, 삭제를 허용하는지 여부를 확인하는 것, 사용자의 신원이 성공적으로 인증 된 후에 발생

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.AntPathMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    // jwt 토큰 검증이 필요없는 url list
    private static final List<String> EXCLUDED_URLS = List.of("/");

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**
     * 인증이나 권한이 필요한 url요청 시 이 필터를 타게됨
     * => jwt 토큰 검증이 필요한 api, url 요청 시 작동
     * => jwt 토큰 검증이 필요없는 api, url 설정가능
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("JwtAuthorizationFilter 동작");

        // jwt 토큰검증 : 정상적 사용자인지 확인





        chain.doFilter(request, response);
    }

    // jwt 토큰 검증이 필요없는 url 추가
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        return EXCLUDED_URLS.stream()
                            .anyMatch(excludeUrl -> antPathMatcher.match(excludeUrl, request.getServletPath()));
    }
}
