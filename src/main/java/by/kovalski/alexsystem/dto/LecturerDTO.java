package by.kovalski.alexsystem.dto;

import by.kovalski.alexsystem.entity.Course;
import by.kovalski.alexsystem.entity.Lecturer;
import by.kovalski.alexsystem.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LecturerDTO {
  private Long id;
  private String firstName;
  private String secondName;
  private String thirdName;
  private String telephoneNumber;
  private String email;
  private Status status;
  private List<String> courses;

  public LecturerDTO(Lecturer lecturer) {
    this.id = lecturer.getId();
    this.firstName = lecturer.getFirstName();
    this.secondName = lecturer.getSecondName();
    this.thirdName = lecturer.getThirdName();
    this.telephoneNumber = lecturer.getTelephoneNumber();
    this.email = lecturer.getEmail();
    this.status = lecturer.getStatus();
    this.courses = lecturer.getCourses().stream().map(Course::getName).toList();
  }
}
