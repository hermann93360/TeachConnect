package persistence;

import model.Teacher;

import java.util.List;
import java.util.Optional;

public interface TeacherData {
    Teacher save(Teacher teacher);
    Optional<Teacher> findById(Long teacherId);
    void delete(Long teacherId);
    List<Teacher> findAll();
    List<Teacher> findByApplyOfferId(Long offerId);
}
