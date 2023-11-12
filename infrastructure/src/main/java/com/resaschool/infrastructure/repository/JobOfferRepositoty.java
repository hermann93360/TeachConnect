package com.resaschool.infrastructure.repository;

import com.resaschool.infrastructure.model.ApplicationEntity;
import com.resaschool.infrastructure.model.JobOfferEntity;
import org.springframework.data.repository.CrudRepository;

public interface JobOfferRepositoty extends CrudRepository<JobOfferEntity, Long> {

}
