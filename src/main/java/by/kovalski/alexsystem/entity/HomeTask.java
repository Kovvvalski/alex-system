package by.kovalski.alexsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "home_tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeTask {

  @Id
  @Column(name = "home_task_id")
  private long id;

  @Column(name = "task")
  private String task;

  @OneToOne(mappedBy = "homeTask")
  private Lesson lesson;
}
