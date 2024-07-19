package by.kovalski.alexsystem.dto;

import by.kovalski.alexsystem.entity.Parent;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ParentDTO extends AbstractPersonDTO {

  public ParentDTO(Parent parent) {
    super(parent.getId(), parent.getFirstName(), parent.getSecondName(), parent.getThirdName(), parent.getTelephoneNumber(),
            parent.getEmail(), parent.getStatus());
  }
}
