package by.kovalski.alexsystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Student extends AbstractPerson {
  @ManyToMany
  private List<Group> groups;

  @ManyToOne
  @JoinColumn(name = "parent_id")
  private Parent parent;
}
