package by.kovalski.alexsystem.dto;

import by.kovalski.alexsystem.entity.Course;
import by.kovalski.alexsystem.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {
  private String name;
  private String description;
  private Status status;

  public CourseDTO(Course course) {
    this.description = course.getDescription();
    this.status = course.getStatus();
  }
}
