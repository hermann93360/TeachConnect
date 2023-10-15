package command;

import lombok.Builder;
import lombok.Data;
import model.User;

@Builder
@Data
public class UpdateUserCommand {
    private Long userId;
    private String login;
    private String firstname;
    private String lastname;
    private String password;

    public User toModel() {
        return new User(userId, login, firstname, lastname, password, null, null);
    }
}
