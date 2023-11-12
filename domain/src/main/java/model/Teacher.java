package model;

import lombok.*;

import java.util.List;

import static model.Application.createNewApplication;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class Teacher {
    private Long teacherId;
    private User user;
    private String specialty;
    private String skills;
    private String graduate;
    private String interest;
    private String wishLevel;
    private String website;
    private String availability;
    private String wishContractType;
    private String otherDataAboutTeacher;
    private String references;
    private List<Application> applications;

    public Application applyFor(JobOffer offer) {
        Application newApplication = createNewApplication(this, offer);
        applications.add(newApplication);
        offer.haveNewApplication(newApplication);
        return newApplication;
    }

    public void removeApplication(Long applicationId) {
        Application application = applications.stream()
                .filter(app -> app.getApplicationId().equals(applicationId))
                .findAny()
                .orElseThrow(() -> {throw new RuntimeException();});

        application.getAssociatedOffer().removeApplication(application);
        applications.remove(application);
    }
}