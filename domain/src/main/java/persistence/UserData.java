package persistence;

import model.User;

import java.util.List;
import java.util.Optional;

public interface UserData {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByLogin(String login);
    void delete(Long userId);
    List<User> findAll();
}