package command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.School;
import model.User;
import model.UserRole;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterSchoolCommand {
    private Long schoolId;
    private String name;
    private String address;
    private String contact;
    private String userFirstname;
    private String userLastname;
    private String userLogin;
    private String userPassword;

    public School toModel() {
        return new School(schoolId, new User(null, userLogin, userFirstname, userLastname, userPassword, UserRole.SCHOOL, null), name, address, contact, null);
    }
}
