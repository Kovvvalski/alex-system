package by.kovalski.alexsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Group {
  @Id
  private String name;

  @ManyToOne
  private Course course;

  @ManyToMany
  private List<Student> students;
}
