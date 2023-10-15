package usecase;

import command.CreateJobOfferCommand;
import command.RegisterSchoolCommand;
import model.JobOffer;
import model.School;
import model.Teacher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JobOfferData;
import persistence.SchoolData;
import persistence.UserData;
import service.SchoolManagementService;
import stubs.InitClass;
import stubs.JobOfferDataStub;
import stubs.SchoolDataStub;
import stubs.UserDataStub;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static stubs.InitClass.initData;

class SchoolManagementTest {

    private SchoolManagement schoolManagement;
    private final JobOfferData jobOfferData = new JobOfferDataStub();
    private final SchoolData schoolData = new SchoolDataStub();
    private final UserData userData = new UserDataStub();

    @BeforeEach
    public void setup() {
        schoolManagement = new SchoolManagementService(jobOfferData, schoolData, userData);
    }

    @BeforeAll
    public static void init() {
        initData();
    }

    @Test
    public void test() {
        assertThat(schoolManagement).isNotNull();

        List<Teacher> teachers = schoolManagement.viewTeachersAppliedForJobOffer(1L);

        assertThat(teachers).isNotEmpty();
        assertThat(teachers).hasSize(1);
    }

    @Test
    public void shouldSaveNewJobOfferAsSchool() {
        CreateJobOfferCommand command = CreateJobOfferCommand.builder()
                .schoolId(1L)
                .description("test")
                .build();

        schoolManagement.postJobOffer(command, 1L);

        List<JobOffer> jobOfferForSchool = schoolManagement.getJobOfferForSchool(1L);
        assertThat(jobOfferForSchool).hasSize(3);
        assertThat(jobOfferForSchool.get(jobOfferForSchool.size()-1).getDescription()).isEqualTo("test");
    }

    @Test
    public void shouldSaveNewSchool() {
        assertThat(schoolManagement.getAllSchool()).hasSize(2);

        RegisterSchoolCommand command = RegisterSchoolCommand.builder()
                .name("EFREI PARIS")
                .address("villjuid")
                .contact("efrei@gmazil.com")
                .userId(2L)
                .build();

        schoolManagement.registerSchool(command);

        List<School> allSchool = schoolManagement.getAllSchool();

        assertThat(allSchool).hasSize(3);
        assertThat(allSchool.get(2).getName()).isEqualTo("EFREI PARIS");

    }

}