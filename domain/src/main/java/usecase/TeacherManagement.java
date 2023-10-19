package usecase;

import command.ApplyForJobOfferCommand;
import command.TeacherControlCommand;
import model.Application;
import model.JobOffer;
import model.Teacher;

import java.util.List;

public interface TeacherManagement {

    Teacher registerTeacher(TeacherControlCommand command);

    void updateTeacherProfile(TeacherControlCommand command);

    void deleteTeacher(Long teacherId);

    List<JobOffer> viewAvailableJobOffers();

    Application applyForJobOffer(ApplyForJobOfferCommand command);

    void retractApplication(Long teacherId, Long applicationId);

    List<JobOffer> viewAppliedJobOffers(Long teacherId);

}
