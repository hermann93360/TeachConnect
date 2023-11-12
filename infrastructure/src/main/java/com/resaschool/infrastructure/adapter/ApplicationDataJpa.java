package com.resaschool.infrastructure.adapter;

import com.resaschool.infrastructure.model.ApplicationEntity;
import com.resaschool.infrastructure.repository.ApplicationRepository;
import model.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.ApplicationData;

@Service
public class ApplicationDataJpa implements ApplicationData {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public Application save(Application application) {
        return applicationRepository.save(ApplicationEntity.fromModel(application)).toModel();
    }

    @Override
    public void delete(Long applicationId) {
        applicationRepository.deleteById(applicationId);
    }
}
