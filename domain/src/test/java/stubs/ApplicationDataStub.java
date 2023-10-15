package stubs;

import model.Application;
import persistence.ApplicationData;

import java.util.HashMap;
import java.util.Map;

public class ApplicationDataStub implements ApplicationData {

    public static Map<Long, Application> applications = new HashMap<>();

    @Override
    public Application save(Application application) {
        if(application.getApplicationId() == null) {
            long id = applications.size() + 1L;
            application.setApplicationId(id);
        }
        applications.put(application.getApplicationId(), application);
        return application;
    }

    @Override
    public void delete(Application application) {
        applications.remove(application.getApplicationId());
    }
}