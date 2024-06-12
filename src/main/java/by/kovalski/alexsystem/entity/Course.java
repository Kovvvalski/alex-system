package by.kovalski.alexsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Course {
  @Id
  private String name;

  @OneToMany
  List<Group> groups;
}
