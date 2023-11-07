package com.relaschool.jee.servlets;

import com.relaschool.jee.factory.TeacherServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.stubs.InitClass;
import usecase.TeacherManagement;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/school/*")
public class SchoolServlet extends HttpServlet {
    private final Map<String, ServletAction> doGetActions = new HashMap<>();
    private final Map<String, ServletAction> doPostActions = new HashMap<>();
    private final Map<String, ServletAction> doDeleteActions = new HashMap<>();
    private final Map<String, ServletAction> doPutActions = new HashMap<>();
    private final TeacherManagement teacherManagement = TeacherServiceFactory.getInstance();

    public SchoolServlet() {
        InitClass.initData();
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
}
