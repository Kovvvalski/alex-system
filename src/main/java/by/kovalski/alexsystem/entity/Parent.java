package by.kovalski.alexsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "parents")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Parent extends AbstractPerson {

  @OneToMany(mappedBy = "parent")
  private List<Student> wards;

  public Parent(Long id, String firstName, String secondName, String thirdName, String telephoneNumber, String email, Status status, List<Student> wards) {
    super(id, firstName, secondName, thirdName, telephoneNumber, email, status);
    this.wards = wards;
  }
}
