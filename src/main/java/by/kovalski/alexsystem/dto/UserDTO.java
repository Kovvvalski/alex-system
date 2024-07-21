package by.kovalski.alexsystem.dto;

import by.kovalski.alexsystem.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
  private String login;

  private String password;

  private Role role;

  private Long dataId;
}
