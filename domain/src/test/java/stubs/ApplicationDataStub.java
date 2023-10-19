package stubs;

import model.Application;
import persistence.ApplicationData;
import persistence.JobOfferData;
import persistence.TeacherData;

import java.util.HashMap;
import java.util.Map;

public class ApplicationDataStub implements ApplicationData {

    public static Map<Long, Application> applications = new HashMap<>();
    private final TeacherData teacherData = new TeacherDataStub();
    private final JobOfferData jobOfferData = new JobOfferDataStub();

    @Override
    public Application save(Application application) {
        if(application.getApplicationId() == null) {
            long id = applications.size() + 1L;
            application.setApplicationId(id);
        }
        applications.put(application.getApplicationId(), application);
        teacherData.save(application.getApplyTeacher());
        jobOfferData.save(application.getAssociatedOffer());
        return application;
    }

    @Override
    public void delete(Long applicationId) {
        Application application = applications.values().stream()
                .filter(x -> x.getApplicationId().equals(applicationId))
                .findAny()
                .get();

        teacherData.save(application.getApplyTeacher());
        jobOfferData.save(application.getAssociatedOffer());
        applications.remove(applicationId);
    }
}