package by.kovalski.alexsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "groups")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group {

  @Id
  private String name;

  @ManyToOne
  @JoinColumn(name = "course_name")
  private Course course;

  @ManyToMany(mappedBy = "groups")
  private List<Student> students;

  @OneToMany(mappedBy = "group")
  private List<Lesson> lessons;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private Status status;
}
