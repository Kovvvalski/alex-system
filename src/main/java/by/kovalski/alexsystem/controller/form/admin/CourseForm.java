package by.kovalski.alexsystem.controller.form.admin;

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
public class CourseForm {
  private String description;
  private Status status;

  public CourseForm(Course course) {
    this.description = course.getDescription();
    this.status = course.getStatus();
  }
}
