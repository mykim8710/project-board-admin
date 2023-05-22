package io.mykim.projectboardadmin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class ViewController {
    @GetMapping("/")
    public String indexView() {
        log.info("[GET] /  >>  root index view");
        return "index";
    }

    @GetMapping("/main")
    public String homeView() {
        log.info("[GET] /main  >>  main view");
        return "main";
    }

}
