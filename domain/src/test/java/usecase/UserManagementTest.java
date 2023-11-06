package usecase;

import command.RegisterUserCommand;
import command.UpdateUserCommand;
import model.User;
import model.UserRole;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JobOfferData;
import persistence.SchoolData;
import persistence.UserData;
import service.UserManagementService;
import service.stubs.JobOfferDataStub;
import service.stubs.SchoolDataStub;
import service.stubs.UserDataStub;

import java.util.Optional;

class UserManagementTest {

    private UserManagement userManagement;
    private final JobOfferData jobOfferData = new JobOfferDataStub();
    private final SchoolData schoolData = new SchoolDataStub();
    private final UserData userData = new UserDataStub();

    @BeforeEach
    public void setup() {
        userManagement = new UserManagementService(userData);
    }

    @Test
    public void shouldRegisterUser() {
        RegisterUserCommand command = RegisterUserCommand.builder()
                .firstname("john")
                .lastname("doe")
                .login("hkamguin@gmail.com")
                .password("hashpass")
                .role(UserRole.TEACHER)
                .build();

        User savedUser = userManagement.registerUser(command);

        Assertions.assertThat(savedUser.getUserId()).isNotNull();
        Assertions.assertThat(savedUser.getLogin()).isEqualTo("hkamguin@gmail.com");
    }

    @Test
    public void shouldUpdateUser() {
        /* Given */
        RegisterUserCommand registerCommand = RegisterUserCommand.builder()
                .firstname("john")
                .lastname("doe")
                .login("hkamguin@gmail.com")
                .password("hashpass")
                .role(UserRole.TEACHER)
                .build();
        User savedUser = userManagement.registerUser(registerCommand);

        /* When */
        UpdateUserCommand updateCommand = UpdateUserCommand.builder()
                .userId(savedUser.getUserId())
                .firstname("john")
                .lastname("doe")
                .login("change@gmail.com")
                .password("hashpass")
                .build();
        User updatedUser = userManagement.updateUser(updateCommand);

        /* Then */
        Assertions.assertThat(updatedUser.getUserId()).isNotNull();
        Assertions.assertThat(updatedUser.getLogin()).isEqualTo("change@gmail.com");
    }

    @Test
    public void shouldDeleteUser() {
        /* Given */
        RegisterUserCommand registerCommand = RegisterUserCommand.builder()
                .firstname("john")
                .lastname("doe")
                .login("hkamguin@gmail.com")
                .password("hashpass")
                .role(UserRole.TEACHER)
                .build();
        User savedUser = userManagement.registerUser(registerCommand);

        /* When */
        userManagement.deleteUser(savedUser.getUserId());
        Optional<User> deletedUser = userManagement.getUserById(savedUser.getUserId());

        /* Then */
        Assertions.assertThat(deletedUser).isNotPresent();
    }

}