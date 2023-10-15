package model;

import lombok.*;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class User {
    private Long userId;
    private String login;
    private String firstName;
    private String lastName;
    private String password;
    private UserRole role;
    private User createdBy;

}
