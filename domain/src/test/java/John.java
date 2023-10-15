import model.User;
import model.UserRole;
import persistence.UserData;

public class John {

    public static User init(){
        return new User(1L, "johnDoe", "John", "Doe", "password", UserRole.SCHOOL, null);
    }
}
