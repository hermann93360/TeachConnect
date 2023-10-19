package usecase;

import command.CreateJobOfferCommand;
import command.RegisterSchoolCommand;
import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JobOfferData;
import persistence.SchoolData;
import persistence.TeacherData;
import persistence.UserData;
import service.SchoolManagementService;
import stubs.JobOfferDataStub;
import stubs.SchoolDataStub;
import stubs.TeacherDataStub;
import stubs.UserDataStub;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static model.UserRole.SCHOOL;
import static model.UserRole.TEACHER;
import static org.assertj.core.api.Assertions.assertThat;

class SchoolManagementTest {

    private SchoolManagement schoolManagement;
    private final JobOfferData jobOfferData = new JobOfferDataStub();
    private final SchoolData schoolData = new SchoolDataStub();
    private final UserData userData = new UserDataStub();
    private final TeacherData teacherData = new TeacherDataStub();

    @BeforeEach
    public void setup() {
        schoolManagement = new SchoolManagementService(jobOfferData, schoolData, userData);
    }

    @AfterEach
    public void init() {
        TeacherDataStub.teachers = new HashMap<>();
        UserDataStub.users = new HashMap<>();
        SchoolDataStub.schools = new HashMap<>();
    }

    @Test
    public void shouldSaveNewJobOfferAsSchool() {
        Long idOfSchoolSaved = createSchoolForTests("EFREI", "villejuif", "01435677").getSchoolId();
        CreateJobOfferCommand command = CreateJobOfferCommand.builder()
                .schoolId(idOfSchoolSaved)
                .description("test")
                .build();

        schoolManagement.postJobOffer(command, idOfSchoolSaved);

        List<JobOffer> jobOfferForSchool = schoolManagement.getJobOfferForSchool(idOfSchoolSaved);
        assertThat(jobOfferForSchool).hasSize(1);
        assertThat(jobOfferForSchool.get(0).getDescription()).isEqualTo("test");
    }

    @Test
    public void shouldSaveNewSchool() {
        RegisterSchoolCommand command = RegisterSchoolCommand.builder()
                .name("EFREI PARIS")
                .address("villjuid")
                .contact("efrei@gmazil.com")
                .userFirstname("firstname")
                .userLastname("lastname")
                .userLogin("login")
                .userPassword("hashpass")
                .build();

        schoolManagement.registerSchool(command);

        List<School> allSchool = schoolManagement.getAllSchool();

        assertThat(allSchool).hasSize(1);
        assertThat(allSchool.get(0).getName()).isEqualTo("EFREI PARIS");
        assertThat(allSchool.get(0).getUser().getLogin()).isEqualTo("login");

    }

    @Test
    public void shouldUpdateSchoolInformations() {
        Long idOfSchoolSaved = createSchoolForTests("EFREI", "villejuif", "01435677").getSchoolId();

        RegisterSchoolCommand command = RegisterSchoolCommand.builder()
                .schoolId(idOfSchoolSaved)
                .name("ESIEE")
                .address("ivry")
                .contact("aaa@gmazil.com")
                .userFirstname("aaa")
                .userLastname("bbb")
                .userLogin("login")
                .userPassword("hashpass")
                .build();

        schoolManagement.updateSchoolDetails(command);

        School school = schoolData.findById(idOfSchoolSaved).get();
        assertThat(school.getUser().getFirstName()).isEqualTo("aaa");
        assertThat(school.getUser().getLastName()).isEqualTo("bbb");
        assertThat(school.getName()).isEqualTo("ESIEE");
    }

    @Test
    public void shouldDeleteTeacher() {
        Long idOfSchoolSaved = createSchoolForTests("Hermann", "Kamguin", "hkamguin@gmail.com").getSchoolId();

        schoolManagement.deleteSchool(idOfSchoolSaved);

        List<School> schoolsAfterSave = schoolData.findAll();
        assertThat(schoolsAfterSave).hasSize(0);
    }

    @Test
    public void shouldViewTeacherWhoApply() {
        School schoolSaved = createSchoolForTests("EFREI", "villejuif", "hkamguin@gmail.com");
        JobOffer jobOfferSaved = createJobOffer(schoolSaved, "chercher prof java");
        Teacher teacher = createTeacher("Hermann", "Kamguin", "aaa@gmail.com");

        schoolManagement.viewTeachersAppliedForJobOffer(jobOfferSaved.getOfferId());
    }

    private Teacher createTeacher(String firstname, String lastname, String login) {
        return teacherData.save(Teacher.builder()
                .user(User.builder()
                        .role(TEACHER)
                        .firstName(firstname)
                        .lastName(lastname)
                        .login(login)
                        .build())
                .build());
    }

    private School createSchoolForTests(String name, String address, String contact) {
        return schoolData.saveOrUpdate(School.builder()
                .name(name)
                .address(address)
                .contact(contact)
                .user(User.builder()
                        .role(SCHOOL)
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