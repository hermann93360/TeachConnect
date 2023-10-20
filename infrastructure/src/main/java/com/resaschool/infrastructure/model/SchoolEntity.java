package com.resaschool.infrastructure.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.School;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "schools")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SchoolEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long schoolId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    private String name;
    private String address;
    private String contact;
    @OneToMany(mappedBy = "school")
    private List<JobOfferEntity> jobOffers;

    public School toModel() {
        return new School(schoolId, user.toModel(), name, address, contact, jobOffers.stream()
                .map(JobOfferEntity::toModel)
                .collect(Collectors.toList()));
    }

    public static SchoolEntity fromModel(School school) {
        return SchoolEntity.builder()
                .schoolId(school.getSchoolId())
                .user(UserEntity.fromModel(school.getUser()))
                .name(school.getName())
                .address(school.getAddress())
                .contact(school.getContact())
                .build();
    }

}