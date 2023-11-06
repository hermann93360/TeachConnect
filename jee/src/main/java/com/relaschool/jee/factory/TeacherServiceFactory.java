package com.relaschool.jee.factory;

import service.TeacherManagementService;
import service.stubs.*;
import usecase.TeacherManagement;

public class TeacherServiceFactory {
    public static TeacherManagement getInstance() {
        return new TeacherManagementService(new JobOfferDataStub(), new SchoolDataStub(), new UserDataStub(), new TeacherDataStub(), new ApplicationDataStub());
    }
}
