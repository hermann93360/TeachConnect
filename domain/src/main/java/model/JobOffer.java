package model;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class JobOffer {
    private Long offerId;
    private String description;
    private Date publicationDate;
    private Date modificationDate;
    private OfferStatus status;
    private List<Application> applications;
    private School createdBy;

}