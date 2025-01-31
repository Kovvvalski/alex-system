package by.kovalski.alexsystem.dto;

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
public class GroupDTO {
  private String name;
  private String courseName;
  private Status status;

  public GroupDTO(Group group) {
    this.courseName = group.getCourse().getName();
    this.status = group.getStatus();
  }
}
