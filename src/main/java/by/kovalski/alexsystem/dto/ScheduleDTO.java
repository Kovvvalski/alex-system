package by.kovalski.alexsystem.dto;


import by.kovalski.alexsystem.entity.Group;
import by.kovalski.alexsystem.entity.Lecturer;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScheduleDTO {
    private Long id;
    private String name;
    private Map<DayOfWeek, LocalTime> schedule;
    private LocalDate scheduleStart;
    private LocalDate scheduleEnd;
    private Long groupId;
    private Long lecturerId;
}
