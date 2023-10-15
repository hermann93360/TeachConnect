package usecase;

import command.CreateJobOfferCommand;
import command.RegisterSchoolCommand;
import command.UpdateJobOfferCommand;
import command.UpdateSchoolCommand;
import model.JobOffer;
import model.School;
import model.Teacher;

import java.util.List;

public interface SchoolManagement {

    School registerSchool(RegisterSchoolCommand command);

    School updateSchoolDetails(UpdateSchoolCommand command);  // We can reuse RegisterSchoolCommand for update

    void deleteSchool(Long schoolId);

    List<Teacher> viewTeachersAppliedForJobOffer(Long offerId);

    List<JobOffer> getJobOfferForSchool(Long schoolId);

    JobOffer postJobOffer(CreateJobOfferCommand command, Long schoolId);

    JobOffer updateJobOffer(UpdateJobOfferCommand command);

    void deleteJobOffer(Long offerId);

    List<School> getAllSchool();

}