package com.relaschool.jee.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@FunctionalInterface
public interface ServletAction {
    void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
