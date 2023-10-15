package command;

import lombok.Builder;
import lombok.Data;
import model.JobOffer;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

@Builder
@Data
public class UpdateJobOfferCommand {
    private Long offerId;

    private String description;

    public JobOffer toModel() {
        return new JobOffer(offerId, description, null, new Date(), null, new ArrayList<>(), null);
    }
}