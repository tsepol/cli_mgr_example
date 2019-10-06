package org.tsepol.cli_mgr_ex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CliMgrExApplication {

    public static void main(String[] args) {
        SpringApplication.run(CliMgrExApplication.class, args);
    }

}
