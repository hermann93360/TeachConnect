package usecase;

import command.RegisterUserCommand;
import command.UpdateUserCommand;
import model.User;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserManagement {

    User registerUser(RegisterUserCommand command);

    User updateUser(UpdateUserCommand command);

    void deleteUser(Long userId);

    Optional<User> getUserById(Long userId);

}