package persistence;

import model.Application;
import model.JobOffer;

public interface ApplicationData {
    Application save(Application application);
    void delete(Application application);

}
