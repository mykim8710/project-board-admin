package io.mykim.projectboardadmin.management.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/management")
public class ManagementController {
    @GetMapping("/articles")
    public String articlesManagementView() {
        log.info("[GET /management/articles  >>  articles management view");
        return "management/articles";
    }

    @GetMapping("/article-comments")
    public String articleCommentsManagementView() {
        log.info("[GET /management/article-comments  >>  article comments management view");
        return "management/article-comments";
    }

    @GetMapping("/hashtags")
    public String hashtagsManagementView() {
        log.info("[GET /management/hashtags  >>  hashtags management view");
        return "management/hashtags";
    }

    @GetMapping("/service-users")
    public String serviceUsersManagementView() {
        log.info("[GET /management/service-users  >>  service users management view");
        return "management/service-users";
    }
}
