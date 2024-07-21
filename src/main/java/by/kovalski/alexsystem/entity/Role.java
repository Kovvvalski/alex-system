package by.kovalski.alexsystem.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
  ADMIN, LECTURER, STUDENT;

  @Override
  public String getAuthority() {
    return name();
  }
}
