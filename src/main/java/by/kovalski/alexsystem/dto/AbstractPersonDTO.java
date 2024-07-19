package by.kovalski.alexsystem.dto;

import by.kovalski.alexsystem.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AbstractPersonDTO {
  private Long id;
  private String firstName;
  private String secondName;
  private String thirdName;
  private String telephoneNumber;
  private String email;
  private Status status;
}
