package service.stubs;

import model.School;
import persistence.SchoolData;
import persistence.UserData;

import java.util.*;

public class SchoolDataStub implements SchoolData {

    public static Map<Long, School> schools = new HashMap<>();

    private final UserData userData = new UserDataStub();

    @Override
    public School saveOrUpdate(School school) {
        if(school.getSchoolId() == null) {
            long id = schools.size() + 1L;
            school.setSchoolId(id);
        }else {
            school.getUser().setUserId(
                    findById(school.getSchoolId())
                            .get()
                            .getUser()
                            .getUserId()
            );
        }

        schools.put(school.getSchoolId(), school);
        userData.save(school.getUser());
        return school;
    }

    @Override
    public Optional<School> findById(Long id) {
        return Optional.ofNullable(schools.get(id));
    }

    @Override
    public void delete(Long schoolId) {
        schools.remove(schoolId);
    }

    @Override
    public List<School> findAll() {
        return new ArrayList<>(schools.values());
    }
}
