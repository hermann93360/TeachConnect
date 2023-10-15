package usecase;

import command.TeacherControlCommand;
import exception.UserException;
import model.Teacher;
import model.User;
import model.UserRole;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JobOfferData;
import persistence.SchoolData;
import persistence.UserData;
import service.TeacherManagementService;
import stubs.JobOfferDataStub;
import stubs.SchoolDataStub;
import stubs.TeacherDataStub;
import stubs.UserDataStub;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TeacherManagementTest {

    private TeacherManagement teacherManagement;
    private final JobOfferData jobOfferData = new JobOfferDataStub();
    private final SchoolData schoolData = new SchoolDataStub();
    private final UserData userData = new UserDataStub();
    private final TeacherDataStub teacherData = new TeacherDataStub();

    @BeforeEach
    public void setup() {
        teacherManagement = new TeacherManagementService(jobOfferData, schoolData, userData, teacherData);
    }

    @AfterEach
    public void init() {
        TeacherDataStub.teachers = new HashMap<>();
        UserDataStub.users = new HashMap<>();
    }

    @Test
    public void shouldRegisterNewTeacher() {
        int initSize = teacherData.findAll().size();
        TeacherControlCommand command = TeacherControlCommand.builder()
                .firstname("john")
                .lastname("doe")
                .login("hkamguin@gmail.com")
                .password("hashpass")
                .specialty("Professeur de maths")
                .build();

        Teacher teacher = teacherManagement.registerTeacher(command);

        assertThat(teacher.getTeacherId()).isNotNull();
        assertThat(teacher.getUser().getUserId()).isNotNull();
        List<Teacher> teachersAfterSave = teacherData.findAll();
        assertThat(teachersAfterSave).hasSize(initSize + 1);
    }

    @Test
    public void shouldNotRegisterNewTeacherIfExist() {
        saveTeacherForTests("Hermann", "Kamguin", "hkamguin@gmail.com");
        int initSize = teacherData.findAll().size();
        TeacherControlCommand command = TeacherControlCommand.builder()
                .firstname("john")
                .lastname("doe")
                .login("hkamguin@gmail.com")
                .password("hashpass")
                .specialty("Professeur de maths")
                .build();

        UserException userException = assertThrows(UserException.class, () -> teacherManagement.registerTeacher(command));

        assertThat(userException).hasMessage("this user already exist");
        List<Teacher> teachersAfterSave = teacherData.findAll();
        assertThat(teachersAfterSave).hasSize(initSize);
    }

    @Test
    public void shouldUpdateTeacherInformations() {
        Long idOfUserSaved = saveTeacherForTests("Hermann", "Kamguin", "hkamguin@gmail.com");
        TeacherControlCommand command = TeacherControlCommand.builder()
                .teacherId(idOfUserSaved)
                .firstname("mickael")
                .lastname("jordon")
                .login("aaa@gmail.com")
                .password("hashpass2")
                .specialty("Professeur de maths")
                .build();

        teacherManagement.updateTeacherProfile(command);

        Teacher teacher = teacherData.findById(idOfUserSaved).get();
        assertThat(teacher.getUser().getFirstName()).isEqualTo("mickael");
        assertThat(teacher.getUser().getLastName()).isEqualTo("jordon");
        assertThat(teacher.getUser().getLogin()).isEqualTo("aaa@gmail.com");
    }

    @Test
    public void shouldNotUpdateTeacherWithEmailNotAvailable() {
        Long idOfUserSaved1 = saveTeacherForTests("Hermann", "Kamguin", "hkamguin@gmail.com");
        saveTeacherForTests("Hermann", "Kamguin", "aaa@gmail.com");
        TeacherControlCommand command = TeacherControlCommand.builder()
                .teacherId(idOfUserSaved1)
                .firstname("mickael")
                .lastname("jordon")
                .login("aaa@gmail.com")
                .password("hashpass2")
                .specialty("Professeur de maths")
                .build();

        UserException userException = assertThrows(UserException.class, () -> teacherManagement.updateTeacherProfile(command));

        assertThat(userException).hasMessage("user with this login exist");
    }

    @Test
    public void shouldDeleteTeacher() {
        Long idOfUserSaved = saveTeacherForTests("Hermann", "Kamguin", "hkamguin@gmail.com");
        int initSize = teacherData.findAll().size();

        teacherManagement.deleteTeacher(idOfUserSaved);

        List<Teacher> teachersAfterSave = teacherData.findAll();
        assertThat(teachersAfterSave).hasSize(initSize - 1);
    }

    @Test
    public void shouldNotDeleteUnknownTeacher() {
        Long idOfUserSaved = 1L;

        UserException userException = assertThrows(UserException.class, () -> teacherManagement.deleteTeacher(idOfUserSaved));

        assertThat(userException).hasMessage("user do not exist");
    }

    private Long saveTeacherForTests(String firstname, String lastname, String login) {
        return teacherData.save(Teacher.builder()
                .user(User.builder()
                        .role(UserRole.TEACHER)
                        .firstName(firstname)
                        .lastName(lastname)
                        .login(login)
                        .build())
                .build()).getTeacherId();
    }

}