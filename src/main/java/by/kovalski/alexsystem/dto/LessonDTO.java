package by.kovalski.alexsystem.dto;

import by.kovalski.alexsystem.entity.Lesson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LessonDTO {
  private Long id;
  private String groupName;
  private LocalDateTime begin;
  private LocalDateTime end;
  private Long lecturerId;
  private String homeTask;

  public LessonDTO(Lesson lesson) {
    id = lesson.getId();
    groupName = lesson.getGroup().getName();
    begin = lesson.getBegin();
    end = lesson.getEnd();
    lecturerId = lesson.getLecturer().getId();
    homeTask = lesson.getHomeTask();
  }

  @Override
  public String toString() {
    return begin.toString();
  }
}
