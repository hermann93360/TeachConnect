package com.relaschool.jee.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.relaschool.jee.factory.SchoolServiceFactory;
import com.relaschool.jee.factory.TeacherServiceFactory;
import com.relaschool.jee.output.JobOfferOutput;
import com.relaschool.jee.output.SchoolOutput;
import command.RegisterSchoolCommand;
import command.TeacherControlCommand;
import command.UpdateSchoolCommand;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.JobOffer;
import model.School;
import service.stubs.InitClass;
import usecase.SchoolManagement;
import usecase.TeacherManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.relaschool.jee.servlets.ServletUtils.*;

@WebServlet("/api/school/*")
public class SchoolServlet extends HttpServlet {
    private final Map<String, ServletAction> doGetActions = new HashMap<>();
    private final Map<String, ServletAction> doPostActions = new HashMap<>();
    private final Map<String, ServletAction> doDeleteActions = new HashMap<>();
    private final Map<String, ServletAction> doPutActions = new HashMap<>();
    private final SchoolManagement schoolManagement = SchoolServiceFactory.getInstance();

    public SchoolServlet() {
        InitClass.initData();
        //doGetActions.put("/job-offers", this::getJobOffers);
        doGetActions.put("/school", this::getAllSchool);
        doDeleteActions.put("/delete", this::deleteSchool);
        doPostActions.put("/create", this::registerSchool);
        doGetActions.put("/view-apply", this::viewTeacherApplied);
        //doPostActions.put("/retract", this::retractForJobOffer);
        doPutActions.put("/update", this::updateSchoolDetails);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        ServletAction action = doGetActions.getOrDefault(pathInfo, this::notFound);
        action.execute(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        ServletAction action = doPostActions.getOrDefault(pathInfo, this::notFound);
        action.execute(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        ServletAction action = doPutActions.getOrDefault(pathInfo, this::notFound);
        action.execute(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        ServletAction action = doDeleteActions.getOrDefault(pathInfo, this::notFound);
        action.execute(req, resp);
    }

    private void notFound(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "La ressource demandée n'est pas disponible.");
    }

    public void registerSchool(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RegisterSchoolCommand command = (RegisterSchoolCommand) getRequestBody(req, RegisterSchoolCommand.class);
        School school = schoolManagement.registerSchool(command);
        createJsonResponse(resp, school);
    }

    private void deleteSchool (HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long schoolId = getLongParameter(req, "schoolId");
        if (schoolId == null) {
            this.notFound(req, resp);
        }
        schoolManagement.deleteSchool(schoolId);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private void updateSchoolDetails (HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RegisterSchoolCommand command = (RegisterSchoolCommand) getRequestBody(req, RegisterSchoolCommand.class);
        schoolManagement.updateSchoolDetails(command);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private void viewTeacherApplied (HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long offerId = getLongParameter(req, "offerId");
        if (offerId == null){
            this.notFound(req, resp);
        }
        schoolManagement.viewTeachersAppliedForJobOffer(offerId);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    public void getAllSchool (HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<SchoolOutput> schools = mapSchoolOutput(schoolManagement.getAllSchool());
        createJsonResponse(resp, schools);
    }
    private List<SchoolOutput> mapSchoolOutput(List<School> schools) {
        return schools.stream()
                .map(SchoolOutput::from)
                .collect(Collectors.toList());
    }

}
