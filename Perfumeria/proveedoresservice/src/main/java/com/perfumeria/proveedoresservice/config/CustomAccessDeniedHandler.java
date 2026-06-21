package com.perfumeria.proveedoresservice.config;
import com.fasterxml.jackson.databind.ObjectMapper; import jakarta.servlet.http.HttpServletRequest; import jakarta.servlet.http.HttpServletResponse; import org.springframework.security.access.AccessDeniedException; import org.springframework.security.web.access.AccessDeniedHandler; import org.springframework.stereotype.Component; import java.io.IOException; import java.util.HashMap; import java.util.Map;
@Component public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException ex) throws IOException {
        res.setContentType("application/json"); res.setStatus(403); Map<String,String> b=new HashMap<>(); b.put("error","Acceso denegado"); b.put("mensaje","No tienes permisos."); b.put("ruta",req.getRequestURI()); new ObjectMapper().writeValue(res.getOutputStream(),b);
    }
}
