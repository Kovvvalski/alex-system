package by.kovalski.alexsystem.handler.impl;

import by.kovalski.alexsystem.entity.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

  private static final String EMPTY = "";

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
          throws IOException {

    var authorities = authentication.getAuthorities();
    var role = authorities.stream().map(r -> r.getAuthority()).findFirst();
    if (role.orElse(EMPTY).equals(Role.ADMIN.name())) {
      response.sendRedirect("/admin");
    } else if (role.orElse(EMPTY).equals(Role.LECTURER.name())) {
      response.sendRedirect("/lecturer");
    } else if (role.orElse(EMPTY).equals(Role.STUDENT.name())) {
      response.sendRedirect("/student");
    } else {
      throw new IOException("Unresolved role " + role.orElse(EMPTY));
    }
  }
}
