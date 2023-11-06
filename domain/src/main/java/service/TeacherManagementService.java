package service;

import command.ApplyForJobOfferCommand;
import command.TeacherControlCommand;
import exception.UserException;
import lombok.AllArgsConstructor;
import model.*;
import persistence.*;
import usecase.TeacherManagement;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static model.OfferStatus.OPEN;

@AllArgsConstructor
public class TeacherManagementService implements TeacherManagement {

    private final JobOfferData jobOfferData;
    private final SchoolData schoolData;
    private final UserData userData;
    private final TeacherData teacherData;
    private final ApplicationData applicationData;

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
        return jobOfferData.findAll().stream()
                .filter(JobOffer::isOpen)
                .collect(Collectors.toList());
    }

    @Override
    public Application applyForJobOffer(ApplyForJobOfferCommand command) {
        Optional<Teacher> applyTeacher = teacherData.findById(command.getTeacherId());
        Optional<JobOffer> applyOffer = jobOfferData.findById(command.getOfferId());

        if(applyTeacher.isEmpty()) {
            throw new RuntimeException("");
        }

        if(applyOffer.isEmpty()) {
            throw new RuntimeException("");
        }

        Application application = applyTeacher
                .get()
                .applyFor(applyOffer.get());

        return applicationData.save(application);
    }

    @Override
    public void retractApplication(Long teacherId, Long applicationId) {
        Optional<Teacher> applyTeacher = teacherData.findById(teacherId);

        if(applyTeacher.isEmpty()) {
            throw new RuntimeException("This teacher do not exist");
        }


        applyTeacher.get().removeApplication(applicationId);
        applicationData.delete(applicationId);
    }

    @Override
    public List<JobOffer> viewAppliedJobOffers(Long teacherId) {
        Optional<Teacher> teacher = teacherData.findById(teacherId);

        if(teacher.isEmpty()) {
            throw new RuntimeException("Teacher do not exist");
        }

        return teacher.get().getApplications().stream()
                .map(Application::getAssociatedOffer)
                .collect(Collectors.toList());
    }
}
