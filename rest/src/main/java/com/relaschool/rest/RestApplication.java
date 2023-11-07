package com.relaschool.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import service.stubs.InitClass;

@SpringBootApplication
public class RestApplication {

    public static void main(String[] args) {
        InitClass.initData();
        SpringApplication.run(RestApplication.class, args);
    }

}
