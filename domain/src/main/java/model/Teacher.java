package model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class Teacher {
    private Long teacherId;
    private User user;
    private String specialty;
    private List<Application> applications;

}