package com.resaschool.infrastructure.adapter;

import model.Teacher;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import persistence.TeacherData;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "database-service", url = "http://localhost:8082")
public interface TeacherDataJpaClient extends TeacherData {

    @Override
    Teacher save(Teacher teacher);

    @Override
    @GetMapping("/teacher")
    Optional<Teacher> findById(Long id);

    @Override
    void delete(Long teacherId);

    @Override
    List<Teacher> findAll();

    @Override
    List<Teacher> findByApplyOfferId(Long offerId);
}
