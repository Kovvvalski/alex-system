package by.kovalski.alexsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ElementCollection
    @CollectionTable(
            name = "schedule_time",
            joinColumns = @JoinColumn(name = "schedule_id")
    )
    @MapKeyColumn(name = "day_of_week")
    @Column(name = "time")
    @Enumerated(EnumType.STRING)
    private Map<DayOfWeek, LocalTime> schedule;

    @Column(name = "schedule_start")
    private LocalDate scheduleStart;

    @Column(name = "schedule_end")
    private LocalDate scheduleEnd;

    @OneToOne(mappedBy = "schedule")
    @EqualsAndHashCode.Exclude
    private Group group;

    @ManyToOne
    @JoinColumn(name = "lecturer_id")
    @EqualsAndHashCode.Exclude
    private Lecturer lecturer;
}
