package com.relaschool.rest.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import service.SchoolManagementService;
import service.TeacherManagementService;
import service.stubs.*;
import usecase.SchoolManagement;
import usecase.TeacherManagement;

@Configuration
public class BeanConfiguration {
    @Bean
    public TeacherManagement teacherManagement() {
        return new TeacherManagementService(new JobOfferDataStub(), new SchoolDataStub(), new UserDataStub(), new TeacherDataStub(), new ApplicationDataStub());
    }

    @Bean
    public SchoolManagement schoolManagement() {
        return new SchoolManagementService(new JobOfferDataStub(), new SchoolDataStub(), new UserDataStub());
    }
}
