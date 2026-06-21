package com.perfumeria.perfumesservice.config;
import jakarta.servlet.FilterChain; import jakarta.servlet.ServletException; import jakarta.servlet.http.HttpServletRequest; import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; import org.springframework.security.core.authority.SimpleGrantedAuthority; import org.springframework.security.core.context.SecurityContextHolder; import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException; import java.util.Arrays; import java.util.List; import java.util.stream.Collectors;
public class RequestHeaderAuthenticationFilter extends OncePerRequestFilter {
    @Override protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        String u=req.getHeader("X-User-Username"); String r=req.getHeader("X-User-Roles");
        if(u!=null&&r!=null){ List<SimpleGrantedAuthority> a=Arrays.stream(r.split(",")).map(String::trim).map(x->new SimpleGrantedAuthority("ROLE_"+x)).collect(Collectors.toList()); SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(u,null,a)); }
        chain.doFilter(req,res);
    }
}
