package by.kovalski.alexsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "lecturers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Lecturer extends AbstractPerson {

  @ManyToMany
  @JoinTable(name = "lecturers_courses",
          joinColumns = @JoinColumn(name = "lecturer_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "course_name", referencedColumnName = "name")
  )
  private List<Course> courses;

  @OneToMany(mappedBy = "lecturer")
  private List<Lesson> lessons;
}
