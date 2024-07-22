package by.kovalski.alexsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Cacheable
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {

  @Id
  @Column(name = "login", unique = true)
  private String login;

  @Column(name = "password")
  private String password;

  @Column(name = "role")
  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToOne
  @JoinColumn(name = "data")
  private AbstractPerson data;
}
