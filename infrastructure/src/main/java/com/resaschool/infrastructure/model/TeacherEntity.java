package com.resaschool.infrastructure.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Teacher;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "teachers")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TeacherEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teacherId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    private String specialty;
    private String skills;
    private String graduate;
    private String interest;
    private String wishLevel;
    private String website;
    private String availability;
    private String wishContractType;
    private String otherDataAboutTeacher;
    private String references;
    @OneToMany(mappedBy = "teacher")
    private List<ApplicationEntity> applications;

    public Teacher toModel() {
        return new Teacher(teacherId, user.toModel(), specialty, skills, graduate, interest, wishLevel, website, availability, wishContractType, otherDataAboutTeacher, references, applications.stream()
                .map(ApplicationEntity::toModel)
                .collect(Collectors.toList()));
    }

    public static TeacherEntity fromModel(Teacher teacher) {
        return TeacherEntity.builder()
                .teacherId(teacher.getTeacherId())
                .user(UserEntity.fromModel(teacher.getUser()))
                .specialty(teacher.getSpecialty())
                .build();

    }

}
