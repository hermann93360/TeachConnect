package persistence;

import model.JobOffer;
import model.Teacher;

import java.util.List;
import java.util.Optional;

public interface JobOfferData {
    JobOffer save(JobOffer jobOffer);
    Optional<JobOffer> findById(Long id);
    void delete(Long offerId);
    List<JobOffer> findAll();
    List<Teacher> findTeacherByApplyOfferId(Long offerId);
}
