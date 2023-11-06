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
    private final TeacherManagement teacherManagement = TeacherServiceFactory.getInstance();

    public TeacherServlet() {
        InitClass.initData();
        doGetActions.put("/job-offers", this::getJobOffers);
        doGetActions.put("/job-offers/apply", this::getApplyJobOffers);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        ServletAction action= doGetActions.getOrDefault(pathInfo, this::notFound);
        action.execute(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        ServletAction action = doGetActions.getOrDefault(pathInfo, this::notFound);
        action.execute(req, resp);
    }

    private void getApplyJobOffers(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long teacherId = Long.valueOf(req.getParameter("teacherId"));

        List<JobOffer> jobOffers = teacherManagement.viewAppliedJobOffers(teacherId);
        List<JobOfferOutput> output = jobOffers.stream()
                .map(JobOfferOutput::from)
                .collect(Collectors.toList());

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(output);

        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }

    private void getJobOffers(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<JobOffer> jobOffers = teacherManagement.viewAvailableJobOffers();
        List<JobOfferOutput> output = jobOffers.stream()
                .map(JobOfferOutput::from)
                .collect(Collectors.toList());

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(output);

        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }

    private void notFound(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "La ressource demandée n'est pas disponible.");
    }
}
