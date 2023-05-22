package io.mykim.projectboardadmin.config.security.jwt.filter;

// Authentication : 인증, 로그인 자격 증명을 확인하여 로그인 한 사용자를 인식하는 것

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mykim.projectboardadmin.config.security.dto.SignInRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /**
     *  default : [POST] /login 요청, 로그인 시도를 위해서 실행되는 함수
     *  {
     *      "username" : "",
     *      "password: : ""
     *  }
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("[POST] /sign-in, JwtAuthenticationFilter 실행 > 인증체크");

        SignInRequestDto signInDto;

        try {
            // 1. request 객체로 부터 username, password get
            signInDto = new ObjectMapper().readValue(request.getInputStream(), SignInRequestDto.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        // 2. signInDto로 UsernamePasswordAuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(signInDto.getUsername(), signInDto.getPassword());

        // authenticationManager로 인증시도를 하면 PrincipalDetailsService - loadUserByUsername()가 호출
        // 만약 AuthenticationProvider를 구현한 CustomAuthenticationProvider가 @Component롤 등록되어있다면 : 구현하지 않는다면 스프링 자체적으로 실행됨
        // CustomAuthenticationProvider의 authenticate() 메서드가 실행됨
        // AuthenticationManager -> AuthenticationProvider, authenticate() 메서드 실행
        AuthenticationManager authenticationManager = getAuthenticationManager();
        return authenticationManager.authenticate(authenticationToken);
    }
}
