package service;

import command.CreateJobOfferCommand;
import command.RegisterSchoolCommand;
import command.UpdateJobOfferCommand;
import exception.JobOfferException;
import exception.SchoolException;
import exception.UserException;
import lombok.AllArgsConstructor;
import model.*;
import persistence.JobOfferData;
import persistence.SchoolData;
import persistence.UserData;
import usecase.SchoolManagement;

import javax.script.ScriptException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class SchoolManagementService implements SchoolManagement {

    private final JobOfferData jobOfferData;
    private final SchoolData schoolData;
    private final UserData userData;

    @Override
    public School registerSchool(RegisterSchoolCommand command) {
        Optional<User> userByLogin = userData.findByLogin(command.getUserLogin());
        if(userByLogin.isPresent()) {
            throw new UserException("this user already exist");
        }

        School school = command.toModel();
        return schoolData.saveOrUpdate(school);
    }

    @Override
    public School updateSchoolDetails(RegisterSchoolCommand command) {
        Optional<User> userByLogin = userData.findByLogin(command.getUserLogin());

        if(userByLogin.isPresent() && !userByLogin.get().getLogin().equalsIgnoreCase(command.getUserLogin())) {
            throw new UserException("user with this login exist");
        }

        School school = command.toModel();
        return schoolData.saveOrUpdate(school);
    }

    @Override
    public void deleteSchool(Long schoolId) {
        Optional<School> school = schoolData.findById(schoolId);

        if(school.isEmpty()) {
            throw new SchoolException("school does not exist");
        }

        schoolData.delete(schoolId);
    }

    @Override
    public List<Teacher> viewTeachersAppliedForJobOffer(Long offerId) {
        Optional<JobOffer> offer = jobOfferData.findById(offerId);

        return offer.map(jobOffer -> jobOffer.getApplications().stream()
                .map(Application::getApplyTeacher)
                .collect(Collectors.toList())).orElse(Collections.emptyList());
    }

    @Override
    public List<JobOffer> getJobOfferForSchool(Long schoolId) {
        Optional<School> school = schoolData.findById(schoolId);

        if(school.isEmpty()) {
            throw new SchoolException("school does not exist");
        }

        return school.get().getJobOffers();
    }

    @Override
    public JobOffer postJobOffer(CreateJobOfferCommand command, Long schoolId) {
        JobOffer jobOffer = command.toModel();
        Optional<School> schoolToPost = schoolData.findById(command.getSchoolId());

        if(schoolToPost.isEmpty()) {
            throw new SchoolException("school does not exist");
        }

        jobOffer.setCreatedBy(schoolToPost.get());
        schoolToPost.get().getJobOffers().add(jobOffer);

        return jobOfferData.save(jobOffer);
    }

    @Override
    public JobOffer updateJobOffer(UpdateJobOfferCommand command) {
        Optional<JobOffer> offer = jobOfferData.findById(command.getOfferId());

        if(offer.isPresent()) {
            throw new JobOfferException("job offer do not exist");
        }

        JobOffer jobOffer = command.toModel();
        return jobOfferData.save(jobOffer);
    }

    @Override
    public void deleteJobOffer(Long offerId) {
        Optional<JobOffer> offer = jobOfferData.findById(offerId);

        if(offer.isPresent()) {
            throw new JobOfferException("job offer do not exist");
        }

        jobOfferData.delete(offerId);
    }

    @Override
    public List<School> getAllSchool() {
        return schoolData.findAll();
    }
}
