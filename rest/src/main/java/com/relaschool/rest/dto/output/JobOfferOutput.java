package com.relaschool.rest.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import model.JobOffer;

import java.util.Date;

@AllArgsConstructor
@Builder
@Data
public class JobOfferOutput {
    private Long offerId;
    private String description;
    private Date publicationDate;
    private String status;
    private String nameOfSchool;

    public static JobOfferOutput from(JobOffer jobOffer) {
        return JobOfferOutput.builder()
                .offerId(jobOffer.getOfferId())
                .description(jobOffer.getDescription())
                .publicationDate(jobOffer.getPublicationDate())
                .status(jobOffer.getStatus().name())
                .nameOfSchool(jobOffer.getCreatedBy().getName())
                .build();
    }
}
