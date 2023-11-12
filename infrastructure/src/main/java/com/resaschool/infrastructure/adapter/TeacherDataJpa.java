package com.resaschool.infrastructure.adapter;

import com.resaschool.infrastructure.model.JobOfferEntity;
import com.resaschool.infrastructure.model.TeacherEntity;
import com.resaschool.infrastructure.repository.TeacherRepository;
import model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.TeacherData;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TeacherDataJpa implements TeacherData {

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public Teacher save(Teacher teacher) {
        return teacherRepository.save(TeacherEntity.fromModel(teacher)).toModel();
    }

    @Override
    public Optional<Teacher> findById(Long teacherId) {
        return teacherRepository.findByTeacherId(teacherId).map(TeacherEntity::toModel);
    }

    @Override
    public void delete(Long teacherId) {
        teacherRepository.deleteById(teacherId);
    }

    @Override
    public List<Teacher> findAll() {
        return StreamSupport.stream(teacherRepository.findAll().spliterator(), false)
                .map(TeacherEntity::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Teacher> findByApplyOfferId(Long offerId) {
        return null;
    }
}
