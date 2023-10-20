package com.relaschool.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManageProfessorController {
    @GetMapping("/create")
    public String create() {
        return "test";
    }
}
