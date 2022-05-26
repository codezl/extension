package com.codezl.dd_team;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.codezl.dd_team.mapper")
public class DdTeamApplication {

    public static void main(String[] args) {
        SpringApplication.run(DdTeamApplication.class, args);
    }

}
