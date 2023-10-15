package command;

import lombok.Builder;
import lombok.Data;
import model.User;
import model.UserRole;

@Builder
@Data
public class RegisterUserCommand {
    private String login;
    private String password;
    private String firstname;
    private String lastname;
    private UserRole role;

    public User toModel() {
        return new User(null, login, firstname, lastname, password, role, null);
    }
}
