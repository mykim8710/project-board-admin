package io.mykim.projectboardadmin;

import io.mykim.projectboardadmin.config.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ViewController {
    private final JwtProvider jwtProvider;

    @GetMapping("/")
    public String indexView() {
        log.info("[GET] /  >>  root index view");
        return "index";
    }

    @GetMapping("/sign-out")
    public void signOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("[GET] /sign-out");
        jwtProvider.initJwtCookie(request, response);
        response.sendRedirect("/");
    }

}
