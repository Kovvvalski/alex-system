package by.kovalski.alexsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Student extends AbstractPerson {
  private static final String PARENT_COL = "parent_id";

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

  @Transient
  private List<Lesson> lessons;

  public List<Lesson> getLessons(){
    if(lessons == null) {
      lessons = new ArrayList<>();
      for(Group group : groups) {
        lessons.addAll(group.getLessons());
      }
    }
    return lessons;
  }
}
