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
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Lecturer extends AbstractPerson {

  @ManyToMany
  @JoinTable(name = "lecturers_courses",
          joinColumns = @JoinColumn(name = "lecturer_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "course_name", referencedColumnName = "name")
  )
  private List<Course> courses;

  @OneToMany(mappedBy = "lecturer")
  @EqualsAndHashCode.Exclude
  private List<Lesson> lessons;
}
