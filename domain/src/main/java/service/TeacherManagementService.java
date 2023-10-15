package service;

import command.ApplyForJobOfferCommand;
import command.TeacherControlCommand;
import exception.UserException;
import lombok.AllArgsConstructor;
import model.Application;
import model.JobOffer;
import model.Teacher;
import model.User;
import persistence.JobOfferData;
import persistence.SchoolData;
import persistence.TeacherData;
import persistence.UserData;
import usecase.TeacherManagement;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class TeacherManagementService implements TeacherManagement {

    private final JobOfferData jobOfferData;
    private final SchoolData schoolData;
    private final UserData userData;
    private final TeacherData teacherData;

    @Override
    public Teacher registerTeacher(TeacherControlCommand command) {
        Optional<User> userByLogin = userData.findByLogin(command.getLogin());

        if(userByLogin.isPresent()) {
            throw new UserException("this user already exist");
        }

        Teacher teacher = command.toModel();
        return teacherData.save(teacher);
    }

    @Override
    public void updateTeacherProfile(TeacherControlCommand command) {
        Optional<User> userByLogin = userData.findByLogin(command.getLogin());

        if(userByLogin.isPresent()) {
            throw new UserException("user with this login exist");
        }

        Teacher teacher = command.toModel();
        teacherData.save(teacher);
    }

    @Override
    public void deleteTeacher(Long teacherId) {
        Optional<Teacher> teacher = teacherData.findById(teacherId);

        if(teacher.isEmpty()) {
            throw new UserException("user do not exist");
        }

        teacherData.delete(teacherId);
    }

    @Override
    public List<JobOffer> viewAvailableJobOffers() {
        return null;
    }

    @Override
    public Application applyForJobOffer(ApplyForJobOfferCommand command) {
        return null;
    }

    @Override
    public void retractApplication(Long applicationId) {

    }

    @Override
    public List<JobOffer> viewAppliedJobOffers(Long teacherId) {
        return null;
    }
}
