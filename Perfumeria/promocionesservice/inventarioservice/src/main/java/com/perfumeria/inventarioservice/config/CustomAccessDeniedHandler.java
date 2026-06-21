package com.perfumeria.inventarioservice.config;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException ex) throws IOException {
        res.setContentType("application/json"); res.setStatus(403);
        Map<String,String> body = new HashMap<>();
        body.put("error","Acceso denegado"); body.put("mensaje","No tienes permisos para acceder a este recurso."); body.put("ruta",req.getRequestURI());
        new ObjectMapper().writeValue(res.getOutputStream(), body);
    }
}
