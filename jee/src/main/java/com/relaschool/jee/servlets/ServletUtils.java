package com.relaschool.jee.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ServletUtils {
    static Object getRequestBody(HttpServletRequest req, Class<?> valueType) throws JsonProcessingException {
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        try (BufferedReader reader = req.getReader()) {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String requestBody = stringBuilder.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(requestBody, valueType);
    }

    static void createJsonResponse(HttpServletResponse resp, Object object) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(object);

        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }

    static Long getLongParameter(HttpServletRequest req, String name) {
        String parameter = req.getParameter(name);
        return parameter == null ? null : Long.valueOf(parameter);
    }

    static void checkParameters(HttpServletRequest req, HttpServletResponse resp, Long... parameters) throws IOException {
        for (Long param : parameters) {
            if (param == null) {
                notFound(req, resp);
            }
        }
    }

    private String getStringParameter(HttpServletRequest req, String name) {
        return req.getParameter(name);
    }

    public static ServletAction notFound(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.sendError(HttpServletResponse.SC_NOT_FOUND, "La ressource demandée n'est pas disponible.");
        return null;
    }
}
