package command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Teacher;
import model.User;
import model.UserRole;

import java.util.ArrayList;
import java.util.Optional;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherControlCommand {
    private Long teacherId;
    private String specialty;
    private String login;
    private String password;
    private String firstname;
    private String lastname;

    public Teacher toModel() {
        return new Teacher(teacherId, new User(null, login, firstname, lastname, password, UserRole.TEACHER, null), specialty, new ArrayList<>());
    }
}
