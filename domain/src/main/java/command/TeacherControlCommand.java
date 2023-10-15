package command;

import lombok.Builder;
import lombok.Data;
import model.Teacher;
import model.User;

@Builder
@Data
public class RegisterTeacherCommand {
    private Long userId;
    private String firstName;
    private String lastName;
    private String specialty;

    public Teacher toModel() {
        return new Teacher(null, new User(userId, null, null, null, null), firstName, lastName, specialty, null);
    }
}
