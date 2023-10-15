package stubs;

import model.*;

import java.util.ArrayList;
import java.util.Date;

import static stubs.ApplicationDataStub.applications;
import static stubs.JobOfferDataStub.jobOffers;
import static stubs.SchoolDataStub.schools;
import static stubs.TeacherDataStub.teachers;
import static stubs.UserDataStub.users;

public class InitClass {
    public static void initData() {

        // Ajout d'utilisateurs
        User schoolUser1 = new User(1L, "schoolUser1", "First Name 1 school", "Last Name 1", "password", UserRole.SCHOOL, null);
        User schoolUser2 = new User(2L, "schoolUser2", "First Name 2", "Last Name 2", "password", UserRole.SCHOOL, null);

        User teacherUser1 = new User(3L, "teacherUser1", "First Name 1", "Last Name 1", "password", UserRole.TEACHER, null);
        User teacherUser2 = new User(4L, "teacherUser2", "First Name 2", "Last Name 2", "password", UserRole.TEACHER, null);
        User teacherUser3 = new User(5L, "teacherUser3", "First Name 3", "Last Name 3", "password", UserRole.TEACHER, null);

        User commercialUser1 = new User(6L, "commercialUser1", "First Name 1 commercial", "Last Name 1 commercial", "password", UserRole.COMMERCIAL, null);
        User commercialUser2 = new User(7L, "commercialUser2", "First Name 2 commercial", "Last Name 2 commercial", "password", UserRole.COMMERCIAL, null);

        users.put(schoolUser1.getUserId(), schoolUser1);
        users.put(schoolUser2.getUserId(), schoolUser2);

        users.put(teacherUser1.getUserId(), teacherUser1);
        users.put(teacherUser2.getUserId(), teacherUser2);
        users.put(teacherUser3.getUserId(), teacherUser3);

        users.put(commercialUser1.getUserId(), commercialUser1);
        users.put(commercialUser2.getUserId(), commercialUser2);

        // Ajout d'écoles
        School school1 = new School(1L, schoolUser1, "School Name 1", "School Address 1", "School Contact 1", new ArrayList<>());
        School school2 = new School(2L, schoolUser2, "School Name 2", "School Address 2", "School Contact 2", new ArrayList<>());

        schools.put(school1.getSchoolId(), school1);
        schools.put(school2.getSchoolId(), school2);

        // Ajout de professeurs
        Teacher teacher1 = new Teacher(1L, teacherUser1, "Specialty 1", new ArrayList<>());
        Teacher teacher2 = new Teacher(2L, teacherUser2, "Specialty 2", new ArrayList<>());
        Teacher teacher3 = new Teacher(3L, teacherUser3, "Specialty 3", new ArrayList<>());

        teachers.put(teacher1.getTeacherId(), teacher1);
        teachers.put(teacher2.getTeacherId(), teacher2);
        teachers.put(teacher3.getTeacherId(), teacher3);

        // Ajout d'offres d'emploi
        JobOffer jobOffer1 = new JobOffer(1L, "Job Description 1", new Date(), new Date(), OfferStatus.OPEN, new ArrayList<>(), school1);
        JobOffer jobOffer2 = new JobOffer(2L, "Job Description 2", new Date(), new Date(), OfferStatus.OPEN, new ArrayList<>(), school1);
        JobOffer jobOffer3 = new JobOffer(3L, "Job Description 3", new Date(), new Date(), OfferStatus.CLOSED, new ArrayList<>(), school2);

        jobOffers.put(jobOffer1.getOfferId(), jobOffer1);
        jobOffers.put(jobOffer2.getOfferId(), jobOffer2);
        jobOffers.put(jobOffer3.getOfferId(), jobOffer3);

        // Ajout d'applications
        Application application1 = new Application(1L, new Date(), ApplicationStatus.PENDING, teacher1, jobOffer1);
        Application application2 = new Application(2L, new Date(), ApplicationStatus.ACCEPTED, teacher2, jobOffer2);
        Application application3 = new Application(3L, new Date(), ApplicationStatus.REFUSED,teacher3, jobOffer3);

        applications.put(application1.getApplicationId(), application1);
        applications.put(application2.getApplicationId(), application2);
        applications.put(application3.getApplicationId(), application3);

        jobOffer1.getApplications().add(application1);
        jobOffer2.getApplications().add(application2);
        jobOffer3.getApplications().add(application3);

        teacher1.getApplications().add(application1);
        teacher2.getApplications().add(application2);
        teacher3.getApplications().add(application3);

        school1.getJobOffers().add(jobOffer1);
        school1.getJobOffers().add(jobOffer2);
        school2.getJobOffers().add(jobOffer3);
    }
}
