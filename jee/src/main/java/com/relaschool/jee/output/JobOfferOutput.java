package com.relaschool.jee.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import model.Application;
import model.JobOffer;
import model.OfferStatus;
import model.School;

import java.util.Date;
import java.util.List;

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
