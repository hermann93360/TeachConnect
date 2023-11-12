package com.resaschool.infrastructure.repository;

import com.resaschool.infrastructure.model.ApplicationEntity;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationRepository extends CrudRepository<ApplicationEntity, Long> {
}
