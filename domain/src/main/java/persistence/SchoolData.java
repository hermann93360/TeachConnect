package persistence;

import model.School;

import java.util.List;
import java.util.Optional;

public interface SchoolData {
    School saveOrUpdate(School school);
    Optional<School> findById(Long id);
    void delete(Long schoolId);
    List<School> findAll();
}
