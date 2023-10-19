package model;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class Application {
    private Long applicationId;
    private Date applicationDate;
    private ApplicationStatus status;
    private Teacher applyTeacher;
    private JobOffer associatedOffer;

    public void configure(Teacher teacher, JobOffer jobOffer) {
        applyTeacher = teacher;
        associatedOffer = jobOffer;
    }

    public static Application createNewApplication(Teacher teacher, JobOffer jobOffer) {
        return new Application(null, new Date(), ApplicationStatus.PENDING, teacher, jobOffer);
    }
}
