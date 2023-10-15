package stubs;

import model.Teacher;
import persistence.TeacherData;
import persistence.UserData;

import java.util.*;

public class TeacherDataStub implements TeacherData {

    public static Map<Long, Teacher> teachers = new HashMap<>();
    public final UserData userData = new UserDataStub();

    @Override
    public Teacher save(Teacher teacher) {
        if(teacher.getTeacherId() == null) {
            long id = teachers.size() + 1L;
            teacher.setTeacherId(id);
        }else {
            teacher.getUser().setUserId(
                    findById(teacher.getTeacherId())
                            .get()
                            .getUser()
                            .getUserId()
            );
        }

        teachers.put(teacher.getTeacherId(), teacher);
        userData.save(teacher.getUser());
        return teacher;
    }

    @Override
    public Optional<Teacher> findById(Long id) {
        return Optional.ofNullable(teachers.get(id));
    }

    @Override
    public void delete(Long teacherId) {
        teachers.remove(teacherId);
    }

    @Override
    public List<Teacher> findAll() {
        return new ArrayList<>(teachers.values());
    }

    @Override
    public List<Teacher> findByApplyOfferId(Long offerId) {
        return null;
    }
}
