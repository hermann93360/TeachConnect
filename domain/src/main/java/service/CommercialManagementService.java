package service;

import command.RegisterSchoolCommand;
import command.TeacherControlCommand;
import model.School;
import model.Teacher;
import usecase.CommercialManagement;

public class CommercialManagementService implements CommercialManagement {
    @Override
    public School addSchoolByCommercial(RegisterSchoolCommand command) {
        return null;
    }

    @Override
    public Teacher addTeacherByCommercial(TeacherControlCommand command) {

        return null;
    }

    @Override
    public void deleteSchoolByCommercial(Long schoolId) {

    }

    @Override
    public void deleteTeacherByCommercial(Long teacherId) {

    }
}
