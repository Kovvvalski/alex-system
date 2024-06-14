package by.kovalski.alexsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "courses")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {

  @Id
  private String name;

  @OneToMany(mappedBy = "course")
  private List<Group> groups;

  @ManyToMany(mappedBy = "courses")
  private List<Lecturer> lecturers;
}
