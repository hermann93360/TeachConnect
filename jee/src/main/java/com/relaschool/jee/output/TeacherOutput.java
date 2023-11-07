package com.relaschool.jee.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Teacher;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TeacherOutput {
    private String firstname;
    private String lastname;
    private String address;
    private String specialty;
    private String contact;
    private String login;

    //Add contact
    public static TeacherOutput from(Teacher teacher) {
        return TeacherOutput.builder()
                .firstname(teacher.getUser().getFirstName())
                .lastname(teacher.getUser().getLastName())
                .address(teacher.getUser().getLastName())
                .specialty(teacher.getSpecialty())
                .contact(teacher.getUser().getLastName())
                .login(teacher.getUser().getLogin())
                .build();
    }
}
