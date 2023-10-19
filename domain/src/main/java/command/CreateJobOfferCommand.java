package command;

import lombok.Builder;
import lombok.Data;
import model.JobOffer;
import model.OfferStatus;

import java.util.ArrayList;
import java.util.Date;

@Builder
@Data
public class CreateJobOfferCommand {
    private Long schoolId;
    private String description;

    public JobOffer toModel() {
        return new JobOffer(null, description, new Date(), null, OfferStatus.OPEN, new ArrayList<>(), null);
    }
}
