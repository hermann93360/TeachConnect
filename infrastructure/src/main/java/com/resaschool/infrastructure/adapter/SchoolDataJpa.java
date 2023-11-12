package com.resaschool.infrastructure.adapter;

import com.resaschool.infrastructure.model.JobOfferEntity;
import com.resaschool.infrastructure.model.SchoolEntity;
import com.resaschool.infrastructure.repository.SchoolRepository;
import model.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.SchoolData;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SchoolDataJpa implements SchoolData {

    @Autowired
    private SchoolRepository schoolRepository;

    @Override
    public School saveOrUpdate(School school) {
        return schoolRepository.save(SchoolEntity.fromModel(school)).toModel();
    }

    @Override
    public Optional<School> findById(Long schoolId) {
        return schoolRepository.findById(schoolId)
                .map(SchoolEntity::toModel);
    }

    @Override
    public void delete(Long schoolId) {
        schoolRepository.deleteById(schoolId);
    }

    @Override
    public List<School> findAll() {
        return StreamSupport.stream(schoolRepository.findAll().spliterator(), false)
                .map(SchoolEntity::toModel)
                .collect(Collectors.toList());
    }
}
