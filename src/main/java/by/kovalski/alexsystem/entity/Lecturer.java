package by.kovalski.alexsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "lecturers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Lecturer extends AbstractPerson {

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "lecturers_courses",
          joinColumns = @JoinColumn(name = "lecturer_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "course_name", referencedColumnName = "name")
  )
  private List<Course> courses;

  @OneToMany(mappedBy = "lecturer")
  @EqualsAndHashCode.Exclude
  private List<Lesson> lessons;

  public Lecturer(Long id, String firstName, String secondName, String thirdName, String telephoneNumber, String email,
                  Status status, List<Course> courses, List<Lesson> lessons) {
    super(id, firstName, secondName, thirdName, telephoneNumber, email, status);
    this.courses = courses;
    this.lessons = lessons;
  }
}
