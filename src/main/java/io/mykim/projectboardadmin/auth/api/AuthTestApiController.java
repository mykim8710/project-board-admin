package io.mykim.projectboardadmin.auth.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AuthTestApiController {

    @GetMapping("/api/v1/auth/permitAll")
    public String permitAllApi() {
        log.info("[GET] /api/v1/auth/permitAll");
        return "permitAll";
    }

    @GetMapping("/api/v1/auth/authenticated")
    public String authenticatedApi() {
        log.info("[GET] /api/v1/auth/authenticated");
        return "authenticated";
    }

    @GetMapping("/api/v1/auth/master")
    public String onlyRoleMasterApi() {
        log.info("[GET] /api/v1/auth/master");
        return "master";
    }
    @GetMapping("/api/v1/auth/manager")
    public String roleManagerApi() {
        log.info("[GET] /api/v1/auth/manager");
        return "manager";
    }
}
