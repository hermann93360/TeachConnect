package com.resaschool.infrastructure.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.*;

import javax.persistence.*;
import java.util.Date;
import java.util.stream.Collectors;

@Entity
@Table(name = "Applications")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApplicationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;
    @ManyToOne
    @JoinColumn(name = "offer_id")
    private JobOfferEntity offer;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private TeacherEntity teacher;
    private Date applicationDate;
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    public Application toModel() {
        Teacher modelTeacher = Teacher.builder()
                .teacherId(teacher.getTeacherId())
                .user(teacher.getUser().toModel())
                .specialty(teacher.getSpecialty())
                .build();

        JobOffer modelOffer = JobOffer.builder()
                .offerId(offer.getOfferId())
                .description(offer.getDescription())
                .publicationDate(offer.getPublicationDate())
                .modificationDate(offer.getModificationDate())
                .status(offer.getStatus())
                .build();

        return new Application(
                applicationId,
                applicationDate,
                status,
                modelTeacher,
                modelOffer);
    }

    public static ApplicationEntity fromModel(Application application) {
        return ApplicationEntity.builder()
                .applicationId(application.getApplicationId())
                .applicationDate(application.getApplicationDate())
                .status(application.getStatus())
                .build();
    }
}
