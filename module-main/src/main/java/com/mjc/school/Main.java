package com.mjc.school;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableJpaAuditing
@EnableWebMvc
@ComponentScan(basePackages = {"com.mjc.school.repository",
        "com.mjc.school.service",
        "com.mjc.school.controller",
        "com.mjc.school"})
public class Main {


    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
