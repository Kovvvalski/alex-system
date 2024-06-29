package by.kovalski.alexsystem.controller.form.admin;

import by.kovalski.alexsystem.entity.Course;
import by.kovalski.alexsystem.entity.Lecturer;
import by.kovalski.alexsystem.entity.Lesson;
import by.kovalski.alexsystem.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LecturerForm {
  private String firstName;
  private String secondName;
  private String thirdName;
  private String telephoneNumber;
  private String email;
  private Status status;
  private List<Course> courses;

  public LecturerForm(Lecturer lecturer) {
    this.firstName = lecturer.getFirstName();
    this.secondName = lecturer.getSecondName();
    this.thirdName = lecturer.getThirdName();
    this.telephoneNumber = lecturer.getTelephoneNumber();
    this.email = lecturer.getEmail();
    this.status = lecturer.getStatus();
    this.courses = lecturer.getCourses();
  }
}
