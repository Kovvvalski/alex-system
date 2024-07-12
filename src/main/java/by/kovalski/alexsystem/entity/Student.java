package by.kovalski.alexsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "students")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Student extends AbstractPerson {

  @ManyToMany
  @JoinTable(name = "students_groups",
          joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "group_name", referencedColumnName = "name"))
  private List<Group> groups;

  @ManyToOne
  @JoinColumn(name = "parent_id")
  private Parent parent;

  @OneToMany(mappedBy = "student")
  private List<Activity> activities;

  @Column(name = "birthdate")
  private LocalDate birthDate;

  public Student(Long id, String firstName, String secondName, String thirdName, String telephoneNumber, String email,
                 Status status, List<Group> groups, Parent parent, List<Activity> activities, LocalDate birthDate) {
    super(id, firstName, secondName, thirdName, telephoneNumber, email, status);
    this.groups = groups;
    this.parent = parent;
    this.activities = activities;
    this.birthDate = birthDate;
  }
}
