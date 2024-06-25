package by.kovalski.alexsystem.controller.form.admin;

import by.kovalski.alexsystem.entity.Course;
import by.kovalski.alexsystem.entity.Group;
import by.kovalski.alexsystem.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupForm {
  private Course course;
  private Status status;

  public GroupForm(Group group) {
    this.course = group.getCourse();
    this.status = group.getStatus();
  }
}
