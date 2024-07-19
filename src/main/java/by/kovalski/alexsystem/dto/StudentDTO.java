package by.kovalski.alexsystem.dto;

import by.kovalski.alexsystem.entity.Group;
import by.kovalski.alexsystem.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO extends AbstractPersonDTO {
  private Long parentId;

  private List<String> groups;

  private LocalDate birthDate;

  public StudentDTO(Student student) {
    super(student.getId(), student.getFirstName(), student.getSecondName(), student.getThirdName(), student.getTelephoneNumber(),
            student.getEmail(), student.getStatus());
    this.groups = student.getGroups().stream().map(Group::getName).toList();
    this.birthDate = student.getBirthDate();
  }
}
