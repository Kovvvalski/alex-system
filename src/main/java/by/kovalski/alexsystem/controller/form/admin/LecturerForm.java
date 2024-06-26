package by.kovalski.alexsystem.controller.form.admin;

import by.kovalski.alexsystem.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
}
