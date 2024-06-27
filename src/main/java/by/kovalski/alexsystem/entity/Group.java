package by.kovalski.alexsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.List;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "groups")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Group {

  @Id
  private String name;

  @ManyToOne
  @JoinColumn(name = "course_name")
  private Course course;

  @ManyToMany(mappedBy = "groups")
  @EqualsAndHashCode.Exclude
  private List<Student> students;

  @OneToMany(mappedBy = "group")
  @EqualsAndHashCode.Exclude
  private List<Lesson> lessons;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private Status status;
}
