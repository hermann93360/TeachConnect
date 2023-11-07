package com.relaschool.jee.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.relaschool.jee.factory.TeacherServiceFactory;
import com.relaschool.jee.output.JobOfferApplyOutput;
import com.relaschool.jee.output.JobOfferOutput;
import com.relaschool.jee.output.TeacherOutput;
import command.ApplyForJobOfferCommand;
import command.TeacherControlCommand;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Application;
import model.JobOffer;
import model.Teacher;
import service.stubs.InitClass;
import usecase.TeacherManagement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import static com.relaschool.jee.servlets.ServletUtils.*;

@WebServlet("/api/teachers/*")
public class TeacherServlet extends HttpServlet {

    private final Map<String, ServletAction> doGetActions = new HashMap<>();
    private final Map<String, ServletAction> doPostActions = new HashMap<>();
    private final Map<String, ServletAction> doDeleteActions = new HashMap<>();
    private final Map<String, ServletAction> doPutActions = new HashMap<>();
    private final TeacherManagement teacherManagement = TeacherServiceFactory.getInstance();

    public TeacherServlet() {
        InitClass.initData();
        doGetActions.put("/job-offers", this::getJobOffers);
        doGetActions.put("/data", this::getTeacherData);
        doDeleteActions.put("/delete", this::deleteTeacher);
        doPostActions.put("/create", this::createTeacher);
        doPostActions.put("/apply", this::applyForJobOffer);
        doPostActions.put("/retract", this::retractForJobOffer);
        doPutActions.put("/update", this::updateTeacherData);
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

    private void updateTeacherData(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TeacherControlCommand command = (TeacherControlCommand) getRequestBody(req, TeacherControlCommand.class);
        teacherManagement.updateTeacherProfile(command);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private void retractForJobOffer(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long teacherId = getLongParameter(req, "teacherId");
        Long applicationId = getLongParameter(req, "applicationId");
        teacherManagement.retractApplication(teacherId, applicationId);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private void getTeacherData(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long teacherId = getLongParameter(req, "teacherId");
        checkParameters(req, resp, teacherId);
        createJsonResponse(resp, TeacherOutput.from(teacherManagement.getTeacherData(teacherId)));
    }

    private void applyForJobOffer(HttpServletRequest req, HttpServletResponse resp) throws JsonProcessingException {

        ApplyForJobOfferCommand command = (ApplyForJobOfferCommand) getRequestBody(req, ApplyForJobOfferCommand.class);
        teacherManagement.applyForJobOffer(command);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private void createTeacher(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        TeacherControlCommand command = (TeacherControlCommand) getRequestBody(req, TeacherControlCommand.class);
        Teacher teacher = teacherManagement.registerTeacher(command);
        createJsonResponse(resp, teacher);
    }

    private void deleteTeacher(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long teacherId = getLongParameter(req, "teacherId");

        if (teacherId == null) {
            this.notFound(req, resp);
        }

        teacherManagement.deleteTeacher(teacherId);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private void getJobOffers(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long teacherId = getLongParameter(req, "teacherId");

        if (teacherId == null) {
            createJsonResponse(resp, mapJobOfferOutput(teacherManagement.viewAvailableJobOffers()));
        } else {
            createJsonResponse(resp, mapJobOfferApplicationOutput(teacherId, teacherManagement.viewAppliedJobOffers(teacherId)));
        }
    }

    private List<JobOfferOutput> mapJobOfferOutput(List<JobOffer> jobOffer) {
        return jobOffer.stream()
                .map(JobOfferOutput::from)
                .collect(Collectors.toList());
    }

    private List<JobOfferApplyOutput> mapJobOfferApplicationOutput(Long teacherId, List<JobOffer> jobOffer) {
        return jobOffer.stream()
                .map(offer -> JobOfferApplyOutput.from(teacherId, offer))
                .collect(Collectors.toList());
    }

    private void notFound(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "La ressource demandée n'est pas disponible.");
    }
}
