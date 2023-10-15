package persistence;

import java.util.List;
import java.util.Optional;

public interface GlobalData<T> {
    T save(T school);
    Optional<T> findById(Long id);
    void delete(T school);
    List<T> findAll();
}
