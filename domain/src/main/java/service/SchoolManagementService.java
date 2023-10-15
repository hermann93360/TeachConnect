package service;

import command.CreateJobOfferCommand;
import command.RegisterSchoolCommand;
import command.UpdateJobOfferCommand;
import command.UpdateSchoolCommand;
import lombok.AllArgsConstructor;
import model.*;
import persistence.JobOfferData;
import persistence.SchoolData;
import persistence.TeacherData;
import persistence.UserData;
import usecase.SchoolManagement;

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
        School schoolToRegister = command.toModel();
        Optional<User> user = userData.findById(command.getUserId());

        if(user.isEmpty()) {
            throw new RuntimeException("");
        }

        schoolToRegister.setUser(user.get());
        return schoolData.save(schoolToRegister);

    }

    @Override
    public School updateSchoolDetails(UpdateSchoolCommand command) {
        return null;
    }

    @Override
    public void deleteSchool(Long schoolId) {

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
            throw new RuntimeException("");
        }

        return school.get().getJobOffers();
    }

    @Override
    public JobOffer postJobOffer(CreateJobOfferCommand command, Long schoolId) {
        JobOffer jobOffer = command.toModel();
        Optional<School> schoolToPost = schoolData.findById(schoolId);

        if(schoolToPost.isEmpty()) {
            throw new RuntimeException("");
        }

        jobOffer.setCreatedBy(schoolToPost.get());
        schoolToPost.get().getJobOffers().add(jobOffer);

        schoolData.save(schoolToPost.get());
        return jobOfferData.save(jobOffer);
    }

    @Override
    public JobOffer updateJobOffer(UpdateJobOfferCommand command) {
        return null;
    }

    @Override
    public void deleteJobOffer(Long offerId) {

    }

    @Override
    public List<School> getAllSchool() {
        return schoolData.findAll();
    }
}
