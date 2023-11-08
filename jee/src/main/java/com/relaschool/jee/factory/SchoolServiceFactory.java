package com.relaschool.jee.factory;

import service.SchoolManagementService;
import service.TeacherManagementService;
import service.stubs.*;
import usecase.SchoolManagement;
import usecase.TeacherManagement;

public class SchoolServiceFactory {
    public static SchoolManagement getInstance() {
        return new SchoolManagementService(new JobOfferDataStub(), new SchoolDataStub(), new UserDataStub());
    }
}
