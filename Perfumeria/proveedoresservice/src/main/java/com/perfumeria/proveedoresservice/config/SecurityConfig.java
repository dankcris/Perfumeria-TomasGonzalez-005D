package com.perfumeria.proveedoresservice.config;
import org.springframework.context.annotation.Bean; import org.springframework.context.annotation.Configuration; import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity; import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity; import org.springframework.security.config.http.SessionCreationPolicy; import org.springframework.security.web.SecurityFilterChain; import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration @EnableWebSecurity
public class SecurityConfig {
    @Bean public SecurityFilterChain filterChain(HttpSecurity http, CustomAccessDeniedHandler accessDeniedHandler) throws Exception {
        http.csrf(c->c.disable()).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(new RequestHeaderAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(auth->auth
                .requestMatchers("/v3/api-docs/**","/swagger-ui/**","/swagger-ui.html").permitAll()
                .requestMatchers(HttpMethod.GET,"/api/proveedores/**").hasAnyRole("ADMIN","VENDEDOR")
                .requestMatchers(HttpMethod.POST,"/api/proveedores/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,"/api/proveedores/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/api/proveedores/**").hasRole("ADMIN")
                .anyRequest().authenticated())
            .exceptionHandling(ex->ex.accessDeniedHandler(accessDeniedHandler));
        return http.build();
    }
}
