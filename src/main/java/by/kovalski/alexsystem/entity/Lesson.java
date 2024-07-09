package by.kovalski.alexsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.time.LocalDateTime;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "lessons")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Lesson {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "lesson_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "group_name")
  @EqualsAndHashCode.Exclude
  private Group group;

  @Column(name = "begin")
  private LocalDateTime begin;

  @Column(name = "end")
  private LocalDateTime end;

  @ManyToOne
  @JoinColumn(name = "lecturer_id")
  private Lecturer lecturer;

  @Column(name = "home_task")
  private String homeTask;

  @Override
  public String toString() {
    return begin.toString();
  }
}
