package by.kovalski.alexsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.StringJoiner;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public abstract class AbstractPerson {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String secondName;

  @Column(name = "third_name")
  private String thirdName;

  @Column(name = "telephone_number", unique = true, nullable = false)
  private String telephoneNumber;

  @Column(name = "email", unique = true, nullable = false)
  private String email;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private Status status;

  @Override
  public String toString() {
    return new StringJoiner(" ")
            .add(firstName)
            .add(secondName)
            .add(thirdName)
            .toString();
  }
}