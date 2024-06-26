package by.kovalski.alexsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "home_tasks")
@Getter
@Setter
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
