package by.kovalski.alexsystem.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScheduleDTO {
  private String groupName;
  private Long lecturerId;
  private LocalDate startDate;
  private LocalDate endDate;
  private Periodicity periodicity;
  private Set<DayOfWeek> daysOfWeek;
  private LocalTime begin;
  private int duration;
}
