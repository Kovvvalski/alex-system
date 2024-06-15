package by.kovalski.alexsystem.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "activities")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
  private Lesson lesson;

  @ManyToOne
  @JoinColumn(name = "student_id")
  private Student student;
}
