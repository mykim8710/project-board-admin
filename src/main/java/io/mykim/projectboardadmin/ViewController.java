package io.mykim.projectboardadmin;

import io.mykim.projectboardadmin.config.security.dto.PrincipalDetail;
import io.mykim.projectboardadmin.config.security.jwt.JwtProvider;
import io.mykim.projectboardadmin.config.security.jwt.enums.TokenType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/main")
    public String homeView(@AuthenticationPrincipal PrincipalDetail principalDetail) {
        log.info("[GET] /main  >>  main view");

        String username = principalDetail.getAdminUser().getUsername();
        System.out.println("username = " + username);

        return "main";
    }

    @GetMapping("/sign-out")
    public void signOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("[GET] /sign-out");
        jwtProvider.initJwtCookie(request, response);
        response.sendRedirect("/");
    }

}
