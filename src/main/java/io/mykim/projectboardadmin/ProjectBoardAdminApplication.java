package io.mykim.projectboardadmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ProjectBoardAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectBoardAdminApplication.class, args);
    }

}
