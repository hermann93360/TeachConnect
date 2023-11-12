package com.resaschool.infrastructure.repository;

import com.resaschool.infrastructure.model.ApplicationEntity;
import com.resaschool.infrastructure.model.SchoolEntity;
import org.springframework.data.repository.CrudRepository;

public interface SchoolRepository extends CrudRepository<SchoolEntity, Long> {
}
