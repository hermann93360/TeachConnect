package com.resaschool.infrastructure.adapter;

import com.resaschool.infrastructure.model.ApplicationEntity;
import com.resaschool.infrastructure.model.JobOfferEntity;
import com.resaschool.infrastructure.model.TeacherEntity;
import com.resaschool.infrastructure.repository.JobOfferRepositoty;
import model.JobOffer;
import model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.JobOfferData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Collections.emptyList;

@Service
public class JobOfferDataJpa implements JobOfferData {

    @Autowired
    private JobOfferRepositoty jobOfferRepositoty;

    @Override
    public JobOffer save(JobOffer jobOffer) {
        return jobOfferRepositoty.save(JobOfferEntity.fromModel(jobOffer)).toModel();
    }

    @Override
    public Optional<JobOffer> findById(Long id) {
        return jobOfferRepositoty.findById(id).map(JobOfferEntity::toModel);
    }

    @Override
    public void delete(Long offerId) {
        jobOfferRepositoty.deleteById(offerId);
    }

    @Override
    public List<JobOffer> findAll() {
        return StreamSupport.stream(jobOfferRepositoty.findAll().spliterator(), false)
                .map(JobOfferEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Teacher> findTeacherByApplyOfferId(Long offerId) {
        Optional<JobOfferEntity> jobOffer = jobOfferRepositoty.findById(offerId);

        return jobOffer.map(jobOfferEntity -> jobOfferEntity.getApplications().stream()
                .map(ApplicationEntity::getTeacher)
                .map(TeacherEntity::toModel)
                .collect(Collectors.toList())).orElse(emptyList());

    }
}
