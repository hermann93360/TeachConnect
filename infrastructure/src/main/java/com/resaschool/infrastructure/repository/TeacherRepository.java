package com.resaschool.infrastructure.repository;

import com.resaschool.infrastructure.model.TeacherEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends CrudRepository<TeacherEntity, Long> {
    Optional<TeacherEntity> findByTeacherId(Long teacherId);
}
