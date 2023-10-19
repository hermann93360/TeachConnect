package persistence;

import model.Application;

public interface ApplicationData {
    Application save(Application application);
    void delete(Long applicationId);

}
