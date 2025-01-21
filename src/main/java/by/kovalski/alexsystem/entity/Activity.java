package by.kovalski.alexsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cache;

import java.time.LocalDate;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
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

    private boolean presence;

    private int mark;

    @ManyToOne
    @JoinColumn(name = "schedule_id")
    @EqualsAndHashCode.Exclude
    private Schedule schedule;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "student_id")
    @EqualsAndHashCode.Exclude
    private Student student;
}
