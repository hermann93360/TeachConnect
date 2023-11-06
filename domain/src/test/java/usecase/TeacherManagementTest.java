package usecase;

import command.ApplyForJobOfferCommand;
import command.TeacherControlCommand;
import exception.UserException;
import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.*;
import service.TeacherManagementService;
import service.stubs.*;

import java.util.*;

import static model.UserRole.TEACHER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TeacherManagementTest {

    private TeacherManagement teacherManagement;
    private final JobOfferData jobOfferData = new JobOfferDataStub();
    private final SchoolData schoolData = new SchoolDataStub();
    private final UserData userData = new UserDataStub();
    private final TeacherData teacherData = new TeacherDataStub();
    private final ApplicationData applicationData = new ApplicationDataStub();

    @BeforeEach
    public void setup() {
        teacherManagement = new TeacherManagementService(jobOfferData, schoolData, userData, teacherData, applicationData);
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
        createTeacher("Hermann", "Kamguin", "hkamguin@gmail.com");
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
        Long idOfUserSaved = createTeacher("Hermann", "Kamguin", "hkamguin@gmail.com").getTeacherId();
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
        Long idOfUserSaved1 = createTeacher("Hermann", "Kamguin", "hkamguin@gmail.com").getTeacherId();
        createTeacher("Hermann", "Kamguin", "aaa@gmail.com");
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
        Long idOfUserSaved = createTeacher("Hermann", "Kamguin", "hkamguin@gmail.com").getTeacherId();
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

    @Test
    public void teacherShouldApplyForAnyJobOffer() {
        School schoolSaved = createSchoolForTests("EFREI", "villejuif", "hkamguin@gmail.com");
        JobOffer jobOfferSaved = createJobOffer(schoolSaved, "chercher prof java");
        Teacher teacher = createTeacher("Hermann", "Kamguin", "hkamguin@gmail.com");

        ApplyForJobOfferCommand command = ApplyForJobOfferCommand.builder()
                .offerId(jobOfferSaved.getOfferId())
                .teacherId(teacher.getTeacherId())
                .build();

        teacherManagement.applyForJobOffer(command);

        Optional<Teacher> teacherAfterApply = teacherData.findById(teacher.getTeacherId());
        Optional<JobOffer> jobOfferAfterApply = jobOfferData.findById(jobOfferSaved.getOfferId());
        assertThat(teacherAfterApply.get().getApplications()).hasSize(1);
        assertThat(jobOfferAfterApply.get().getApplications()).hasSize(1);
    }

    @Test
    public void teacherShouldViewApplyJob() {
        School schoolSaved = createSchoolForTests("EFREI", "villejuif", "hkamguin@gmail.com");
        JobOffer jobOfferSaved = createJobOffer(schoolSaved, "chercher prof java");
        Teacher teacher = createTeacher("Hermann", "Kamguin", "hkamguin@gmail.com");
        apply(jobOfferSaved, teacher);

        List<JobOffer> jobOffers = teacherManagement.viewAppliedJobOffers(teacher.getTeacherId());

        assertThat(jobOffers).hasSize(1);
    }

    @Test
    public void teacherShouldRemoveApply() {
        School schoolSaved = createSchoolForTests("EFREI", "villejuif", "hkamguin@gmail.com");
        JobOffer jobOfferSaved = createJobOffer(schoolSaved, "chercher prof java");
        Teacher teacher = createTeacher("Hermann", "Kamguin", "hkamguin@gmail.com");
        Application apply = apply(jobOfferSaved, teacher);

        teacherManagement.retractApplication(teacher.getTeacherId(), apply.getApplicationId());

        List<JobOffer> jobOffers = getTeacherApplied(teacher.getTeacherId());
        assertThat(jobOffers).hasSize(0);
    }

    private List<JobOffer> getTeacherApplied(Long teacherId) {
        return teacherManagement.viewAppliedJobOffers(teacherId);
    }

    private Application apply(JobOffer jobOfferSaved, Teacher teacher) {
        Application application = teacher.applyFor(jobOfferSaved);
        applicationData.save(application);
        teacherData.save(teacher);
        jobOfferData.save(jobOfferSaved);
        return application;
    }

    private Teacher createTeacher(String firstname, String lastname, String login) {
        return teacherData.save(Teacher.builder()
                .user(User.builder()
                        .role(TEACHER)
                        .firstName(firstname)
                        .lastName(lastname)
                        .login(login)
                        .build())
                .applications(new ArrayList<>())
                .build());
    }

    private School createSchoolForTests(String name, String address, String contact) {
        return schoolData.saveOrUpdate(School.builder()
                .name(name)
                .address(address)
                .contact(contact)
                .user(User.builder()
                        .role(TEACHER)
                        .firstName("firstname")
                        .lastName("lastname")
                        .login("login")
                        .password("hash")
                        .build())
                .jobOffers(new ArrayList<>())
                .build());
    }

    public JobOffer createJobOffer(School associatedSchool, String description) {
        return jobOfferData.save(JobOffer.builder()
                .createdBy(associatedSchool)
                .applications(new ArrayList<>())
                .description(description)
                .publicationDate(new Date())
                .status(OfferStatus.OPEN)
                .modificationDate(null)
                .build());
    }

}