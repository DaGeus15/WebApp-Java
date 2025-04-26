package com.SOA.controller;

import com.google.gson.Gson;
import com.SOA.model.User;
import com.SOA.service.UserService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;

@WebServlet("/user/api")
public class UserController extends HttpServlet {
    private final UserService userService = new UserService();
    private final Gson gson = new Gson();

    // Método auxiliar para enviar la respuesta
    private void sendResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        response.getWriter().write("{\"message\":\"" + message + "\"}");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Obtener usuarios desde el servicio
        var users = userService.getAll();
        
        // Convertir a JSON y enviar la respuesta
        String json = gson.toJson(users);
        response.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Leer los datos del cuerpo de la solicitud
        BufferedReader reader = request.getReader();
        User user = gson.fromJson(reader, User.class);

        // Guardar usuario usando el servicio
        boolean created = userService.save(user);

        // Enviar respuesta según si fue creado o no
        if (created) {
            sendResponse(response, HttpServletResponse.SC_CREATED, "Usuario creado con éxito");
        } else {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Error al crear usuario");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Leer los datos del cuerpo de la solicitud
        BufferedReader reader = request.getReader();
        User user = gson.fromJson(reader, User.class);

        // Actualizar usuario usando el servicio
        boolean updated = userService.update(user);

        // Enviar respuesta según si fue actualizado o no
        if (updated) {
            sendResponse(response, HttpServletResponse.SC_OK, "Usuario actualizado con éxito");
        } else {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Error al actualizar usuario");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Leer los datos del cuerpo de la solicitud
        BufferedReader reader = request.getReader();
        User user = gson.fromJson(reader, User.class);

        // Eliminar usuario usando el servicio
        boolean deleted = userService.delete(user);

        // Enviar respuesta según si fue eliminado o no
        if (deleted) {
            sendResponse(response, HttpServletResponse.SC_OK, "Usuario eliminado con éxito");
        } else {
            sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, "Error al eliminar usuario");
        }
    }
}
