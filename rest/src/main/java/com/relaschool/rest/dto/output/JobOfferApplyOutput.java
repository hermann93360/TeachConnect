package com.relaschool.rest.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import model.Application;
import model.JobOffer;

import java.util.Date;

@AllArgsConstructor
@Builder
@Data
public class JobOfferApplyOutput {
    private Long offerId;
    private String description;
    private Date publicationDate;
    private String applyStatus;
    private String nameOfSchool;
    private Long applicationId;

    public static JobOfferApplyOutput from(Long teacherId, JobOffer jobOffer) {

        Application application = jobOffer.getApplications().stream()
                .filter(app -> app.getApplyTeacher().getTeacherId().equals(teacherId))
                .findFirst()
                .get();

        return JobOfferApplyOutput.builder()
                .offerId(jobOffer.getOfferId())
                .description(jobOffer.getDescription())
                .publicationDate(jobOffer.getPublicationDate())
                .nameOfSchool(jobOffer.getCreatedBy().getName())
                .applicationId(application.getApplicationId())
                .applyStatus(application.getStatus().name())
                .build();
    }
}
