package by.kovalski.alexsystem.dto;

import by.kovalski.alexsystem.entity.Parent;
import by.kovalski.alexsystem.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ParentDTO {

  private Long id;
  private String firstName;
  private String secondName;
  private String thirdName;
  private String telephoneNumber;
  private String email;
  private Status status;

  public ParentDTO(Parent parent) {
    this.id = parent.getId();
    this.firstName = parent.getFirstName();
    this.secondName = parent.getSecondName();
    this.thirdName = parent.getThirdName();
    this.telephoneNumber = parent.getTelephoneNumber();
    this.email = parent.getEmail();
    this.status = parent.getStatus();
  }
}
