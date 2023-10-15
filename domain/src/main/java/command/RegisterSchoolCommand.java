package command;

import lombok.Builder;
import lombok.Data;
import model.School;
import model.User;

@Builder
@Data
public class RegisterSchoolCommand {
    private Long userId;
    private String name;
    private String address;
    private String contact;

    public School toModel() {
        return new School(null, new User(userId, null, null, null, null, null, null), name, address, contact, null);
    }
}
