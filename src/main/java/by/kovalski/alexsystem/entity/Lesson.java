package by.kovalski.alexsystem.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Lesson {
  long id;
  Group group;
  LocalDateTime begin;
  LocalDateTime end;
}
