package com.resaschool.infrastructure.model;

import lombok.*;
import model.User;
import model.UserRole;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String login;
    private String firstName;
    private String lastName;
    private String password;
    private String phone;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @ManyToOne
    @JoinColumn(name = "created_by")
    private UserEntity createdBy;

    public User toModel() {
        return new User(userId, login, password, firstName, lastName, role, createdBy.toModel(), phone);
    }

    public static UserEntity fromModel(User user) {
        return UserEntity.builder()
                .userId(user.getUserId())
                .login(user.getLogin())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .createdBy(user.getCreatedBy() != null ? UserEntity.fromModel(user.getCreatedBy()) : null)
                .build();
    }

}