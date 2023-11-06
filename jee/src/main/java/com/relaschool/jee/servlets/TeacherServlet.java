package com.relaschool.jee.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.relaschool.jee.factory.TeacherServiceFactory;
import com.relaschool.jee.output.JobOfferOutput;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.JobOffer;
import service.stubs.InitClass;
import usecase.TeacherManagement;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

@WebServlet("/api/teachers/*")
public class TeacherServlet extends HttpServlet {

    private final Map<String, ServletAction> doGetActions = new HashMap<>();
    private final Map<String, ServletAction> doPostActions = new HashMap<>();
    private final Map<String, ServletAction> doDeleteActions = new HashMap<>();
    private final TeacherManagement teacherManagement = TeacherServiceFactory.getInstance();

    public TeacherServlet() {
        InitClass.initData();
        doGetActions.put("/job-offers", this::getJobOffers);
        doDeleteActions.put("/", this::deleteTeacher);
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
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        ServletAction action = doDeleteActions.getOrDefault(pathInfo, this::notFound);
        action.execute(req, resp);
    }

    private void deleteTeacher(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long teacherId = getLongParameter(req, "teacherId");

        if(teacherId == null) {
            this.notFound(req, resp);
        }

        teacherManagement.deleteTeacher(teacherId);
    }

    private void getJobOffers(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long teacherId = getLongParameter(req, "teacherId");

        if(teacherId == null) {
            createJsonResponse(resp, mapJobOfferOutput(teacherManagement.viewAvailableJobOffers()));
        }else{
            createJsonResponse(resp, mapJobOfferOutput(teacherManagement.viewAppliedJobOffers(teacherId)));
        }
    }

    private void createJsonResponse(HttpServletResponse resp, Object object) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(object);

        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }

    private Long getLongParameter(HttpServletRequest req, String name) {
        String parameter = req.getParameter(name);
        return parameter == null ? null : Long.valueOf(parameter);
    }

    private String getStringParameter(HttpServletRequest req, String name) {
        return req.getParameter(name);
    }

    private List<JobOfferOutput> mapJobOfferOutput(List<JobOffer> jobOffer) {
        return jobOffer.stream()
                .map(JobOfferOutput::from)
                .collect(Collectors.toList());
    }

    private void notFound(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "La ressource demandée n'est pas disponible.");
    }
}
