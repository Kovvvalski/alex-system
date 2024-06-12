package by.kovalski.alexsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Parent extends AbstractPerson {
  @OneToMany(mappedBy = "parent")
  private List<Student> wards;
}
