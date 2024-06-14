package by.kovalski.alexsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractPerson {

  @Column(name = "login")
  private String login;

  @Column(name = "password")
  private String password;

  @Column(name = "role")
  @Enumerated(EnumType.STRING)
  private Role role;
}
