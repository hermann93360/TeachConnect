package service;

import command.RegisterUserCommand;
import command.UpdateUserCommand;
import lombok.AllArgsConstructor;
import model.User;
import persistence.JobOfferData;
import persistence.SchoolData;
import persistence.UserData;
import usecase.SchoolManagement;
import usecase.UserManagement;

import java.util.Optional;

@AllArgsConstructor
public class UserManagementService implements UserManagement {

    private final UserData userData;

    @Override
    public User registerUser(RegisterUserCommand command) {
        User userToSave = command.toModel();
        return userData.save(userToSave);
    }

    @Override
    public User updateUser(UpdateUserCommand command) {
        User userToUpdate = command.toModel();
        return userData.save(userToUpdate);
    }

    @Override
    public void deleteUser(Long userId) {
        userData.delete(userId);
    }

    @Override
    public Optional<User> getUserById(Long userId) {
        return userData.findById(userId);
    }
}
