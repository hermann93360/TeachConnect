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

}
