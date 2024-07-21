package by.kovalski.alexsystem.service;

import by.kovalski.alexsystem.dto.CourseDTO;
import by.kovalski.alexsystem.entity.Course;

import java.util.List;

public interface CourseService extends BaseService<Course, String, CourseDTO> {
  List<Course> findAllActive();
}
