package com.resaschool.infrastructure.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Application;
import model.JobOffer;
import model.OfferStatus;
import model.School;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "JobOffers")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JobOfferEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long offerId;
    @ManyToOne
    @JoinColumn(name = "school_id")
    private SchoolEntity school;
    private String description;
    private Date publicationDate;
    private Date modificationDate;
    @Enumerated(EnumType.STRING)
    private OfferStatus status;
    @OneToMany(mappedBy = "offer")
    private List<ApplicationEntity> applications;

    public JobOffer toModel() {
        School modelSchool = School.builder()
                .schoolId(school.getSchoolId())
                .user(school.getUser().toModel())
                .name(school.getName())
                .address(school.getAddress())
                .contact(school.getContact())
                .build();

        return new JobOffer(offerId, description, publicationDate, modificationDate, status,
                applications.stream().map(ApplicationEntity::toModel).collect(Collectors.toList()), modelSchool);
    }

    public static JobOfferEntity fromModel(JobOffer jobOffer) {
        return JobOfferEntity.builder()
                .offerId(jobOffer.getOfferId())
                .description(jobOffer.getDescription())
                .publicationDate(jobOffer.getPublicationDate())
                .modificationDate(jobOffer.getModificationDate())
                .status(jobOffer.getStatus())
                .applications(jobOffer.getApplications().stream().
                        map(ApplicationEntity::fromModel).collect(Collectors.toList()))
                .school(SchoolEntity.fromModel(jobOffer.getCreatedBy()))
                .build();
    }
}
