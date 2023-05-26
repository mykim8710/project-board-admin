package io.mykim.projectboardadmin.config.security;

import io.mykim.projectboardadmin.global.response.CommonResponseUtils;
import io.mykim.projectboardadmin.config.security.handler.CustomAccessDeniedHandler;
import io.mykim.projectboardadmin.config.security.handler.CustomAuthenticationEntryPoint;
import io.mykim.projectboardadmin.config.security.handler.CustomAuthenticationFailureHandler;
import io.mykim.projectboardadmin.config.security.handler.CustomAuthenticationSuccessHandler;
import io.mykim.projectboardadmin.config.security.jwt.JwtProvider;
import io.mykim.projectboardadmin.config.security.jwt.filter.JwtAuthenticationFilter;
import io.mykim.projectboardadmin.config.security.jwt.filter.JwtAuthorizationFilter;
import io.mykim.projectboardadmin.config.security.jwt.service.JwtRefreshTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 활성화 => 기본 스프링 필터체인에 등록
public class SpringSecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtRefreshTokenService jwtRefreshTokenService;
    private final JwtProvider jwtProvider;
    private final CommonResponseUtils commonResponseUtils;

    // add bean : password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // add bean : AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // add bean : CustomAuthenticationSuccessHandler
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler(jwtProvider, jwtRefreshTokenService, commonResponseUtils);
    }

    // add bean : CustomAuthenticationFailureHandler
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler(commonResponseUtils);
    }

    // add bean : CustomAccessDeniedHandler
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler(commonResponseUtils);
    }

    // add bean : CustomAuthenticationEntryPoint
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint(commonResponseUtils);
    }

    /**
     * add bean : JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter
     *  - [POST] /sign-in 요청 시 작동하는 필터
     *  - UsernamePasswordAuthenticationFilter는 httpSecurity.formLogin(), [POST] /login [username, password]일때 작동
     *  - 현재는 jwt방식을 사용하기 때문에 formLogin().disable() => UsernamePasswordAuthenticationFilter는 동작하지않음
     *  - UsernamePasswordAuthenticationFilter 상속받아 jwt 사용 시 로그인 용 필터를 구현 => security 에 등록하여 작동
     *  - 로그인(인증, 신원확인)을 요청하는 사용자의 정보로 UsernamePasswordAuthenticationToken 발급
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager());               // set authenticationManager
        jwtAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler()); // set AuthenticationSuccessHandler(인증 성공 핸들러)
        jwtAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler()); // set AuthenticationFailureHandler(인증 실패 핸들러)
        jwtAuthenticationFilter.afterPropertiesSet();
        return jwtAuthenticationFilter;
    }

    /**
     * add bean : JwtAuthorizationFilter extends BasicAuthenticationFilter
     * - 인증이나 권한이 필요한 url요청 시 이 필터를 타게됨
     * - jwt 검증이 필요한 api, url 요청 시 작동
     * - jwt 검증이 필요없는 api, url 설정가능
     */
    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() throws Exception {
        return new JwtAuthorizationFilter(authenticationManager(), jwtProvider, jwtRefreshTokenService);
    }

    @Bean
    public SecurityFilterChain filterChainSession(HttpSecurity httpSecurity) throws Exception {
        // csrf token disable, because use jwt token
        httpSecurity
                .csrf()
                .disable();

        // add filter
        httpSecurity
                .addFilter(jwtAuthenticationFilter())   // extends UsernamePasswordAuthenticationFilter
                .addFilter(jwtAuthorizationFilter());   // extends BasicAuthenticationFilter < OncePerRequestFilter

        httpSecurity
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // session not used
                .and()
                .formLogin().disable()  // form tag login not used
                .httpBasic().disable();

        httpSecurity
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())            // 권한실패
                .authenticationEntryPoint(authenticationEntryPoint()); // 인증실패

        httpSecurity
                .authorizeRequests()


                // Admin Management > 관리자 계정 관리
                .antMatchers(HttpMethod.POST, "/api/v1/admin-users").hasRole("MASTER")
                .antMatchers(HttpMethod.GET, "/api/v1/admin-users/duplicate-check").hasRole("MASTER")
                .antMatchers(HttpMethod.GET, "/api/v1/admin-users").authenticated()


                .antMatchers(HttpMethod.GET,"/admin/**").authenticated()
                .antMatchers(HttpMethod.GET,"/management/**").authenticated()


                .antMatchers(HttpMethod.GET,"/error-page/*", "/").permitAll()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll();  // static resource

        return httpSecurity.build();
    }

}
