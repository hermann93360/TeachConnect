package com.relaschool.rest.controller;

import org.springframework.web.bind.annotation.RestController;
import usecase.SchoolManagement;

@RestController
public class SchoolController {

    private final SchoolManagement schoolManagement;

    public SchoolController(SchoolManagement schoolManagement) {
        this.schoolManagement = schoolManagement;
    }
}
