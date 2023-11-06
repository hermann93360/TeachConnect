package service.stubs;

import model.Application;
import model.JobOffer;
import model.Teacher;
import persistence.JobOfferData;
import persistence.SchoolData;

import java.util.*;
import java.util.stream.Collectors;

public class JobOfferDataStub implements JobOfferData {

    public static Map<Long, JobOffer> jobOffers = new HashMap<>();

    public final SchoolData schoolData = new SchoolDataStub();

    @Override
    public JobOffer save(JobOffer jobOffer) {
        if(jobOffer.getOfferId() == null) {
            long id = jobOffers.size() + 1L;
            jobOffer.setOfferId(id);
        }

        jobOffers.put(jobOffer.getOfferId(), jobOffer);
        schoolData.saveOrUpdate(jobOffer.getCreatedBy());
        return jobOffer;
    }

    @Override
    public Optional<JobOffer> findById(Long id) {
        return Optional.ofNullable(jobOffers.get(id));
    }

    @Override
    public void delete(Long offerId) {
        jobOffers.remove(offerId);
    }

    @Override
    public List<JobOffer> findAll() {
        return new ArrayList<>(jobOffers.values());
    }

    @Override
    public List<Teacher> findTeacherByApplyOfferId(Long offerId) {
        return jobOffers.get(offerId).getApplications().stream()
                .map(Application::getApplyTeacher)
                .collect(Collectors.toList());
    }
}