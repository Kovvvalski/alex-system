package by.kovalski.alexsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course {
  private static final int DESCRIPTION_LENGTH = 1000;

  @Id
  private String name;

  @Column(length = DESCRIPTION_LENGTH)
  private String description;

  @OneToMany(mappedBy = "course")
  private List<Group> groups;

  @ManyToMany(mappedBy = "courses")
  private List<Lecturer> lecturers;
}
