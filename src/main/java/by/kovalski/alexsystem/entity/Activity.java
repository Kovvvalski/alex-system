package by.kovalski.alexsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "activities")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Activity {

  @Id
  @Column(name = "mark_id")
  private long id;

  @Column(name = "presence")
  private boolean presence;

  @Column(name = "mark")
  private int mark;

  @ManyToOne
  @JoinColumn(name = "lesson_id")
  @EqualsAndHashCode.Exclude
  private Lesson lesson;

  @ManyToOne
  @JoinColumn(name = "student_id")
  @EqualsAndHashCode.Exclude
  private Student student;
}
