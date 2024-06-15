package by.kovalski.alexsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractPerson {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private long id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String secondName;

  @Column(name = "third_name")
  private String thirdName;

  @Column(name = "telephone_number")
  private String telephoneNumber;

  @Column(name = "email")
  private String email;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private Status status;
}