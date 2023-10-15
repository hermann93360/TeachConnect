package stubs;

import model.User;
import persistence.UserData;

import java.util.*;

public class UserDataStub implements UserData {

    public static Map<Long, User> users = new HashMap<>();

    @Override
    public User save(User user) {
        if(user.getUserId() == null) {
            long id = users.size() + 1L;
            user.setUserId(id);
        }
        users.put(user.getUserId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return users.values().stream()
                .filter(user -> user.getLogin().equalsIgnoreCase(login))
                .findAny();
    }

    @Override
    public void delete(Long userId) {
        users.remove(userId);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }
}
