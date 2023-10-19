package command;

import lombok.Builder;
import lombok.Getter;
import model.Application;
import model.ApplicationStatus;
import model.JobOffer;
import model.Teacher;

import java.util.Date;

@Getter
@Builder
public class ApplyForJobOfferCommand {
    private Long teacherId;
    private Long offerId;

    public Application constructModel(Teacher teacher, JobOffer jobOffer) {
        return new Application(null, new Date(), ApplicationStatus.PENDING, teacher, jobOffer);
    }
}
