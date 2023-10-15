package command;

import model.Application;
import model.ApplicationStatus;
import model.Teacher;

import java.util.Date;

public class ApplyForJobOfferCommand {
    private Long teacherId;
    private Long offerId;

    public Application toModel() {
        return new Application(null, new Date(), ApplicationStatus.PENDING, null, null);
    }
}
