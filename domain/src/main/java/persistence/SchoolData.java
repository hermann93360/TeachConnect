package persistence;

import model.School;

import java.util.List;
import java.util.Optional;

public interface SchoolData {
    School save(School school);
    Optional<School> findById(Long id);
    void delete(School school);
    List<School> findAll();
}
