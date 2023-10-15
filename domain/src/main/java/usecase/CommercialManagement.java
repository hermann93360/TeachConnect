package usecase;

import command.RegisterSchoolCommand;
import command.TeacherControlCommand;
import model.School;
import model.Teacher;

public interface CommercialManagement {

    School addSchoolByCommercial(RegisterSchoolCommand command);

    Teacher addTeacherByCommercial(TeacherControlCommand command);

    void deleteSchoolByCommercial(Long schoolId);

    void deleteTeacherByCommercial(Long teacherId);

}
