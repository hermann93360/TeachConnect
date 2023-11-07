package com.resaschool.infrastructure.adapter;

import com.resaschool.infrastructure.model.TeacherEntity;
import com.resaschool.infrastructure.repository.TeacherRepository;
import model.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import persistence.TeacherData;

import java.util.List;
import java.util.Optional;

@Service
public class TeacherDataJpa implements TeacherData {

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    public Teacher save(Teacher teacher) {
        return teacherRepository.save(TeacherEntity.fromModel(teacher)).toModel();
    }

    @Override
    public Optional<Teacher> findById(Long id) {
        return teacherRepository.findByTeacherId(id).map(TeacherEntity::toModel);
    }

    @Override
    public void delete(Long teacherId) {

    }

    @Override
    public List<Teacher> findAll() {
        return null;
    }

    @Override
    public List<Teacher> findByApplyOfferId(Long offerId) {
        return null;
    }
}
