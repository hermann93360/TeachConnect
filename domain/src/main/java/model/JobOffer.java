package model;

import lombok.*;

import java.util.Date;
import java.util.List;

import static model.OfferStatus.OPEN;

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

    public void haveNewApplication(Application application) {
        applications.add(application);
    }

    public void removeApplication(Application application) {
        applications.remove(application);
    }

    public boolean isOpen(){
        return status.equals(OPEN);
    }
}