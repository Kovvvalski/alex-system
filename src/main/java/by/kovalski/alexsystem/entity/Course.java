package by.kovalski.alexsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.List;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "courses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Course {
  private static final int DESCRIPTION_LENGTH = 1000;

  @Id
  @Column(name="course_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Column(length = DESCRIPTION_LENGTH)
  private String description;

  @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
  @EqualsAndHashCode.Exclude
  private List<Group> groups;

  @ManyToMany(mappedBy = "courses", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  private List<Lecturer> lecturers;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  private Status status;
}
